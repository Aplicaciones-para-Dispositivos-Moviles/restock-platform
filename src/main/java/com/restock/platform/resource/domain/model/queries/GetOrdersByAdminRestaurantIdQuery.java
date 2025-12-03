package com.restock.platform.resource.domain.model.queries;

public record GetOrdersByAdminRestaurantIdQuery(Long adminRestaurantId) {
    public GetOrdersByAdminRestaurantIdQuery {
        if (adminRestaurantId == null || adminRestaurantId <= 0) {
            throw new IllegalArgumentException("Admin Restaurant ID must be a positive number");
        }
    }
}
