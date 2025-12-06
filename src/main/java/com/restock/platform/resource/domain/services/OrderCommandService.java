package com.restock.platform.resource.domain.services;

import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.commands.CreateOrderCommand;
import com.restock.platform.resource.domain.model.commands.UpdateOrderCommand;
import com.restock.platform.resource.domain.model.commands.UpdateOrderStateCommand;

import java.util.Optional;

/**
 * Command service interface for handling operations related to orders to suppliers.
 */
public interface OrderCommandService {

    /**
     * Handles the creation of an order to a supplier.
     *
     * @param command the command containing the creation data
     * @return the ID of the newly created Order
     */
    Long handle(CreateOrderCommand command);

    /**
     * Handles the update of an order's state and situation.
     *
     * @param command the command containing the new state and situation
     * @return an Optional containing the updated Order, if found
     */
    Optional<Order> handle(UpdateOrderStateCommand command);

    /**
     * Deletes an order by ID.
     *
     * @param orderId the ID of the order to delete
     */
    void delete(Long orderId);

    Optional<Order> handle(UpdateOrderCommand command);

}
