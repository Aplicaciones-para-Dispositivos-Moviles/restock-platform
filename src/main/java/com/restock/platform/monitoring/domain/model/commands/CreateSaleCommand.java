package com.restock.platform.monitoring.domain.model.commands;

import java.util.List;

public record CreateSaleCommand(
        List<DishSelectionCommand> dishSelections,
        List<SupplySelectionCommand> supplySelections,
        Double subtotal,
        Double taxes,
        Double totalCost,
        Integer userId
) {
    public CreateSaleCommand {
        if (subtotal == null || subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }
        if (taxes == null || taxes < 0) {
            throw new IllegalArgumentException("Taxes cannot be negative");
        }
        if (totalCost == null || totalCost < 0) {
            throw new IllegalArgumentException("Total cost cannot be negative");
        }
        if ((dishSelections == null || dishSelections.isEmpty()) &&
            (supplySelections == null || supplySelections.isEmpty())) {
            throw new IllegalArgumentException("Sale must have at least one dish or supply");
        }
    }
}
