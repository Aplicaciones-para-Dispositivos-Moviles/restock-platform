package com.restock.platform.monitoring.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record SaleResource(
        Long id,
        String saleNumber,
        Double totalCost,
        Double subtotal,
        Double taxes,
        Date registeredDate,
        Integer userId,
        String status,
        List<DishSelectionResource> dishSelections,
        List<SupplySelectionResource> supplySelections
) {
}
