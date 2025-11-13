package com.restock.platform.monitoring.domain.model.commands;
public record SupplySelectionCommand(
    Long supplyId,
    Integer quantity,
    Double unitPrice
) {
    public SupplySelectionCommand {
        if (supplyId == null || supplyId <= 0) {
            throw new IllegalArgumentException("Supply ID must be positive");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
    }
}
