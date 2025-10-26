package com.restock.platform.monitoring.interfaces.rest.resources;

// Lo que guardamos de un sale item
public record SaleItemResource(
        String   saleItemId,
        Long   recipeId,
        Integer quantity,
        Double subTotalPrice
) {
}
