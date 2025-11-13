package com.restock.platform.monitoring.interfaces.rest.resources;
public record DishSelectionResource(
    Long dishId,
    Integer quantity,
    Double unitPrice
) {
}
