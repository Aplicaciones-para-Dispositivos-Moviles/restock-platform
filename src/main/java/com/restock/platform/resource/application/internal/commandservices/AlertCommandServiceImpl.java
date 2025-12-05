package com.restock.platform.resource.application.internal.commandservices;

import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.commands.UpdateAlertCommand;
import com.restock.platform.resource.domain.model.commands.UpdateOrderStateCommand;
import com.restock.platform.resource.domain.model.entities.Alert;
import com.restock.platform.resource.domain.services.AlertCommandService;
import com.restock.platform.resource.domain.services.OrderCommandService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.AlertRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements AlertCommandService {

    private final OrderRepository orderRepository;
    private final AlertRepository alertRepository;
    private final OrderCommandService orderCommandService;

    public AlertCommandServiceImpl(OrderRepository orderRepository,
                                   AlertRepository alertRepository,
                                   OrderCommandService orderCommandService) {
        this.orderRepository = orderRepository;
        this.alertRepository = alertRepository;
        this.orderCommandService = orderCommandService;
    }

    @Override
    public Optional<Order> handle(UpdateAlertCommand command) {

        Alert alert = alertRepository.findById(command.alertId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Alert not found with id: " + command.alertId()));

        Long orderId = alert.getOrderId();

        UpdateOrderStateCommand updateOrderStateCommand =
                new UpdateOrderStateCommand(
                        orderId,
                        command.newState(),
                        command.newSituation()
                );

        Optional<Order> updatedOrderOpt = orderCommandService.handle(updateOrderStateCommand);

        updatedOrderOpt.ifPresent(updatedOrder -> {
            alert.updateStatus(
                    updatedOrder.getState(),
                    updatedOrder.getSituation()
            );
            alertRepository.save(alert);
        });

        return updatedOrderOpt;
    }

}
