package com.restock.platform.monitoring.domain.model.queries;
public record GetAllSalesByUserIdQuery(Integer userId) {
    public GetAllSalesByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }
}
