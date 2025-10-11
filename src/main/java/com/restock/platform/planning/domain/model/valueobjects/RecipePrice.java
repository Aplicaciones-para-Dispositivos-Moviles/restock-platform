package com.restock.platform.planning.domain.model.valueobjects;

public record RecipePrice(Double price) {

    public RecipePrice {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
    }

    public RecipePrice() {
        this(0.0);
    }

    public double getPrice() {
        return price;
    }
}
