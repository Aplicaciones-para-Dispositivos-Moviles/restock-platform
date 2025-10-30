package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleItemResource;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleResource;

import java.util.List;

public class SaleResourceFromEntityAssembler{
    public static SaleResource toResourceFromEntity(Sale sale) {
        List<SaleItemResource> items = sale.getItems().stream()
                .map(SaleItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new SaleResource(
                sale.getId(),
                sale.getSaleCode().saleCodeId(),
                sale.getUserId(),
                sale.getTotalPrice(),
                items
        );
    }
}
