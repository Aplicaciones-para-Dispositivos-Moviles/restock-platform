package com.restock.platform.monitoring.interfaces.rest.resources;

import java.util.List;

public record CreateSaleResource(
    List<DishSelectionResource> dishSelections,
    List<SupplySelectionResource> supplySelections,
    Double subtotal,
    Double taxes,
    Double totalCost,
    Integer userId
) {
}

