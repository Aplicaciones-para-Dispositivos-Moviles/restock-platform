package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.queries.GetAllOrdersQuery;
import com.restock.platform.resource.domain.model.queries.GetOrderByIdQuery;
import com.restock.platform.resource.domain.model.queries.GetOrdersByAdminRestaurantIdQuery;
import com.restock.platform.resource.domain.model.queries.GetOrdersBySupplierIdQuery;
import com.restock.platform.resource.domain.services.OrderQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.BatchRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.CustomSupplyRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.OrderRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final BatchRepository batchRepository;
    private final CustomSupplyRepository customSupplyRepository;
    private final SupplyRepository supplyRepository;

    public OrderQueryServiceImpl(OrderRepository orderRepository,
                                 BatchRepository batchRepository,
                                 CustomSupplyRepository customSupplyRepository,
                                 SupplyRepository supplyRepository) {
        this.orderRepository = orderRepository;
        this.batchRepository = batchRepository;
        this.customSupplyRepository = customSupplyRepository;
        this.supplyRepository = supplyRepository;
    }

    private CustomSupply loadCustomSupply(Long customSupplyId) {
        return customSupplyRepository.findById(customSupplyId)
                .map(cs -> {
                    cs.setSupply(supplyRepository.findById(cs.getSupplyId()).orElse(null));
                    return cs;
                })
                .orElse(null);
    }

    private Batch loadBatch(Long batchId) {
        return batchRepository.findById(batchId)
                .map(batch -> {
                    batch.setCustomSupply(loadCustomSupply(batch.getCustomSupplyId()));
                    return batch;
                })
                .orElse(null);
    }

    @Override
    public List<Order> handle(GetAllOrdersQuery query) {
        var orders = orderRepository.findAll();

        orders.forEach(order ->
                order.getBatchItems().forEach(item -> {
                    var batch = loadBatch(item.getBatchId());
                    item.setBatch(batch);
                })
        );

        return orders;
    }

    @Override
    public Optional<Order> handle(GetOrderByIdQuery query) {
        return orderRepository.findById(query.id())
                .map(order -> {
                    order.getBatchItems().forEach(item -> {
                        var batch = loadBatch(item.getBatchId());
                        item.setBatch(batch);
                    });
                    return order;
                });
    }

    @Override
    public List<Order> handle(GetOrdersBySupplierIdQuery query) {
        var orders = orderRepository.findAllBySupplierId(query.supplierId());

        orders.forEach(order ->
                order.getBatchItems().forEach(item -> {
                    var batch = loadBatch(item.getBatchId());
                    item.setBatch(batch);
                })
        );

        return orders;
    }

    @Override
    public List<Order> handle(GetOrdersByAdminRestaurantIdQuery query) {
        var orders = orderRepository.findAllByAdminRestaurantId(query.adminRestaurantId());

        orders.forEach(order ->
                order.getBatchItems().forEach(item -> {
                    var batch = loadBatch(item.getBatchId());
                    item.setBatch(batch);
                })
        );

        return orders;
    }

}
