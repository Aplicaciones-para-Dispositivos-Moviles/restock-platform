package com.restock.platform.planning.domain.model.valueobjects;

public record CatalogSupplyId(int value) {
    public CatalogSupplyId {
        if (value <= 0)
            throw new IllegalArgumentException("Supply ID must be a positive number.");
    }
}

