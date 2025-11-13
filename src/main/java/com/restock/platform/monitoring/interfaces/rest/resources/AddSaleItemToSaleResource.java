package com.restock.platform.monitoring.interfaces.rest.resources;

//Lo que necesitarom para agregar un item a sale
public record AddSaleItemToSaleResource(
        Long recipeId,
        Integer quantity
) {
    public AddSaleItemToSaleResource {
        if (recipeId == null || recipeId <= 0)
            throw new IllegalArgumentException("Recipe ID must be a positive number.");
        if (quantity == null || quantity < 0)
            throw new IllegalArgumentException("Quantity must be non-negative");
    }
}
