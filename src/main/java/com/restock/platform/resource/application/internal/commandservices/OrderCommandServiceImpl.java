package com.restock.platform.resource.application.internal.commandservices;

import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import com.restock.platform.resource.domain.model.commands.CreateOrderCommand;
import com.restock.platform.resource.domain.model.commands.UpdateOrderCommand;
import com.restock.platform.resource.domain.model.commands.UpdateOrderStateCommand;
import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;
import com.restock.platform.resource.domain.services.OrderCommandService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.BatchRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.CustomSupplyRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.OrderRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final BatchRepository batchRepository;
    private final CustomSupplyRepository customSupplyRepository;
    private final UserRepository userRepository;


    public OrderCommandServiceImpl(OrderRepository orderRepository,
                                   SequenceGeneratorService sequenceGeneratorService,
                                   BatchRepository batchRepository,
                                   CustomSupplyRepository customSupplyRepository,
                                   UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.batchRepository = batchRepository;
        this.customSupplyRepository = customSupplyRepository;
        this.userRepository = userRepository;
    }


    private void transferStockFromSupplierToRestaurant(Order order) {
        Long supplierId = order.getSupplierId();
        Long restaurantId = order.getAdminRestaurantId();

        for (var item : order.getBatchItems()) {
            var batch = batchRepository.findById(item.getBatchId())
                    .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + item.getBatchId()));

            if (!batch.getUserId().equals(supplierId)) {
                throw new IllegalStateException("Batch " + batch.getId() + " does not belong to supplier " + supplierId);
            }

            if (batch.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock in supplier batch " + batch.getId());
            }

            //Descontar del supplier
            batch.setStock(batch.getStock() - item.getQuantity());
            batchRepository.save(batch);

            //Agregar al restaurant
            var restaurantBatch = batchRepository.findAllByUserId(restaurantId)
                    .stream()
                    .filter(b -> b.getCustomSupplyId().equals(batch.getCustomSupplyId()))
                    .findFirst();

            if (restaurantBatch.isPresent()) {
                var existingBatch = restaurantBatch.get();
                existingBatch.setStock(existingBatch.getStock() + item.getQuantity());
                batchRepository.save(existingBatch);
            } else {
                var customSupply = customSupplyRepository.findById(batch.getCustomSupplyId())
                        .orElseThrow(() -> new IllegalArgumentException("CustomSupply not found for new restaurant batch"));

                var restaurantUser = userRepository.findById(restaurantId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + restaurantId));

                var newBatch = new Batch(
                        restaurantId,
                        restaurantUser.getRole().getId(),
                        customSupply,
                        item.getQuantity(),
                        batch.getExpirationDate()
                );
                newBatch.setId(sequenceGeneratorService.generateSequence("batches_sequence"));
                batchRepository.save(newBatch);
            }
        }
    }


    @Override
    public Long handle(CreateOrderCommand command) {
        var order = new Order(command);
        order.setId(sequenceGeneratorService.generateSequence("orders_sequence"));

        if (command.batchItems() != null && !command.batchItems().isEmpty()) {

            var combinedItems = command.batchItems().stream()
                    .collect(Collectors.toMap(
                            OrderBatchItem::getBatchId,
                            item -> new OrderBatchItem(
                                    item.getBatchId(),
                                    item.getQuantity(),
                                    item.isAccept()
                            ),
                            (existing, incoming) -> {
                                existing.setQuantity(existing.getQuantity() + incoming.getQuantity());
                                existing.setAccept(existing.isAccept() || incoming.isAccept());
                                return existing;
                            }
                    ));

            var batchIds = combinedItems.keySet().stream().toList();
            var existingBatches = batchRepository.findAllById(batchIds);

            if (existingBatches.size() != batchIds.size()) {
                throw new IllegalArgumentException("One or more batches do not exist.");
            }

            boolean allBelongToSupplier = existingBatches.stream()
                    .allMatch(batch -> batch.getUserId().equals(command.supplierId()));

            if (!allBelongToSupplier) {
                throw new IllegalArgumentException("Some batches do not belong to supplier id: " + command.supplierId());
            }

            double totalPrice = 0.0;
            double totalRequested = 0.0;

            for (var item : combinedItems.values()) {
                var batch = existingBatches.stream()
                        .filter(b -> b.getId().equals(item.getBatchId()))
                        .findFirst()
                        .orElseThrow();

                var customSupply = customSupplyRepository.findById(batch.getCustomSupplyId())
                        .orElseThrow(() -> new IllegalArgumentException("CustomSupply not found for batch " + batch.getId()));

                batch.setCustomSupply(customSupply);
                item.setBatch(batch);

                double pricePerUnit = customSupply.getPrice().amount();
                totalPrice += pricePerUnit * item.getQuantity();
                totalRequested += item.getQuantity();

                order.addBatchItem(item);
            }

            order.setTotalPrice(BigDecimal.valueOf(totalPrice));
            order.setRequestedProductsCount((int) totalRequested);
        }

        order.finalizeOrderTotals();
        orderRepository.save(order);
        return order.getId();
    }


    @Override
    public Optional<Order> handle(UpdateOrderStateCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + command.orderId()));

        order.update(command.newState(), command.newSituation());
        orderRepository.save(order);

        if (command.newState() == OrderToSupplierState.DELIVERED) {
            transferStockFromSupplierToRestaurant(order);
        }

        return Optional.of(order);
    }

    @Override
    public void delete(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order not found with id: " + orderId);
        }
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting order: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Order> handle(UpdateOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found with id: " + command.orderId()
                ));

        order.applyUpdate(
                command.description(),
                command.estimatedShipDate(),
                command.estimatedShipTime(),
                command.batchItems()
        );

        orderRepository.save(order);
        return Optional.of(order);
    }

}
