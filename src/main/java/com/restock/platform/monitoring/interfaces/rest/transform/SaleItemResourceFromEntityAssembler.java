package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.entities.SaleItem;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleItemResource;

public class SaleItemResourceFromEntityAssembler{
    public static SaleItemResource toResourceFromEntity(SaleItem entity) {
        return new SaleItemResource(
                entity.saleItemIdToString(),
                entity.getRecipeId(),
                entity.getQuantity(),
                entity.getSubTotalPrice()
        );
    }
}
