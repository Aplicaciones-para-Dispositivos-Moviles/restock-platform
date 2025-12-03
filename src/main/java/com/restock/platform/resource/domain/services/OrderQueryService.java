package com.restock.platform.resource.domain.services;

import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.queries.GetAllOrdersQuery;
import com.restock.platform.resource.domain.model.queries.GetOrdersByAdminRestaurantIdQuery;
import com.restock.platform.resource.domain.model.queries.GetOrdersBySupplierIdQuery;
import com.restock.platform.resource.domain.model.queries.GetOrderByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service interface for retrieving Order entities.
 */
public interface OrderQueryService {

    /**
     * Handles retrieval of all orders to suppliers.
     *
     * @param query the query object (empty)
     * @return list of all Order entities
     */
    List<Order> handle(GetAllOrdersQuery query);
    Optional<Order> handle(GetOrderByIdQuery query);

    List<Order> handle(GetOrdersBySupplierIdQuery query);

    List<Order> handle(GetOrdersByAdminRestaurantIdQuery query);

}
