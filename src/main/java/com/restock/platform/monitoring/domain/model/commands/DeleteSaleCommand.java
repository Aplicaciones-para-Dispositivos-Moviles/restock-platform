package com.restock.platform.monitoring.domain.model.commands;

public record DeleteSaleCommand(Long saleId) {
    public DeleteSaleCommand {
        if (saleId == null || saleId <= 0) {
            throw new IllegalArgumentException("Sale ID must be positive");
        }
    }
}

