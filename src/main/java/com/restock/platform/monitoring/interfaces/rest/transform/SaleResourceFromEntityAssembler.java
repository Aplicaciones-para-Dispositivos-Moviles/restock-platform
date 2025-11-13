package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.interfaces.rest.resources.DishSelectionResource;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleResource;
import com.restock.platform.monitoring.interfaces.rest.resources.SupplySelectionResource;

import java.util.List;
import java.util.stream.Collectors;

public class SaleResourceFromEntityAssembler {
    public static SaleResource toResourceFromEntity(Sale sale) {
        List<DishSelectionResource> dishSelections = sale.getDishSelections().stream()
            .map(dish -> new DishSelectionResource(
                dish.getDishId(),
                dish.getQuantity(),
                dish.getUnitPrice()
            ))
            .collect(Collectors.toList());

        List<SupplySelectionResource> supplySelections = sale.getSupplySelections().stream()
            .map(supply -> new SupplySelectionResource(
                supply.getSupplyId(),
                supply.getQuantity(),
                supply.getUnitPrice()
            ))
            .collect(Collectors.toList());

        return new SaleResource(
            sale.getId(),
            sale.getSaleNumber().getValue(),
            sale.getTotalCost(),
            sale.getSubtotal(),
            sale.getTaxes(),
            sale.getRegisteredDate(),
            sale.getUserId(),
            sale.getStatus().toString(),
            dishSelections,
            supplySelections
        );
    }
}
