package com.restock.platform.monitoring.domain.model.queries;
public record GetSaleByIdAndUserIdQuery(Long saleId, Integer userId) {
    public GetSaleByIdAndUserIdQuery {
        if (saleId == null || saleId <= 0) {
            throw new IllegalArgumentException("Sale ID must be positive");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }
}
