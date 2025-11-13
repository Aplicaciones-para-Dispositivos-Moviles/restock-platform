package com.restock.platform.monitoring.domain.model.queries;

public record GetSaleByNumberQuery(String saleNumber) {
    public GetSaleByNumberQuery {
        if (saleNumber == null || saleNumber.isBlank()) {
            throw new IllegalArgumentException("Sale number cannot be null or empty");
        }
    }
}

