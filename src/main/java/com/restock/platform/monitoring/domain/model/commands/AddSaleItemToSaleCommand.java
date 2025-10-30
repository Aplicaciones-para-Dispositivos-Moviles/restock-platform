package com.restock.platform.monitoring.domain.model.commands;

import com.restock.platform.monitoring.domain.model.valueobjects.SaleItemId;

public record AddSaleItemToSaleCommand(
        Long saleId,
        Long recipeId,
        Integer quantity
) {
    public AddSaleItemToSaleCommand {
        if (saleId == null || saleId <= 0)
            throw new IllegalArgumentException("Sale ID must be a positive number.");
        if (quantity == null || quantity < 0)
            throw new IllegalArgumentException("Quantity must be non-negative");
    }
}
