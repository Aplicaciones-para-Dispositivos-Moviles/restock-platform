package com.restock.platform.monitoring.domain.model.commands;
public record DishSelectionCommand(
    Long dishId,
    Integer quantity,
    Double unitPrice
) {
    public DishSelectionCommand {
        if (dishId == null || dishId <= 0) {
            throw new IllegalArgumentException("Dish ID must be positive");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
    }
}
