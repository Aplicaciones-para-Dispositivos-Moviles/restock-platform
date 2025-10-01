package com.restock.platform.planning.domain.model.valueobjects;

public record RecipeSupplyQuantity(Double supplyQuantity) {
    public RecipeSupplyQuantity {
        if (supplyQuantity == null || supplyQuantity < 0) {
            throw new IllegalArgumentException("Supply quantity must not be null or negative");
        }
    }

    public RecipeSupplyQuantity() {
        this(0.0);
    }

    public Double getSupplyQuantity() {
        return supplyQuantity;
    }
}
