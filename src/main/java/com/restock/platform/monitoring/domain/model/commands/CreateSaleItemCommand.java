package com.restock.platform.monitoring.domain.model.commands;

public record CreateSaleItemCommand(Long recipeId, Integer quantity, Double price) {
}
