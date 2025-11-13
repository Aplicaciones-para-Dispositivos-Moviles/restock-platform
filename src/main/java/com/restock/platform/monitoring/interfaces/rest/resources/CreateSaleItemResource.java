package com.restock.platform.monitoring.interfaces.rest.resources;

//Lo que necesitamos para crear un sale item
public record CreateSaleItemResource(
        Long recipeId,
        Integer quantity,
        Double price

) {
    public CreateSaleItemResource {
        if (recipeId == null || recipeId <= 0)
            throw new IllegalArgumentException("Recipe ID must be a positive number");
        if (quantity == null || quantity < 0)
            throw new IllegalArgumentException("Quantity must be non-negative");
        if (price == null || price < 0)
            throw new IllegalArgumentException("Price must be non-negative");
    }
}
