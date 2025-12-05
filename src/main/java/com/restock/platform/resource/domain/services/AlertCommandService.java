package com.restock.platform.resource.domain.services;

import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.commands.UpdateAlertCommand;

import java.util.Optional;

public interface AlertCommandService {
    Optional<Order> handle(UpdateAlertCommand command);
}
