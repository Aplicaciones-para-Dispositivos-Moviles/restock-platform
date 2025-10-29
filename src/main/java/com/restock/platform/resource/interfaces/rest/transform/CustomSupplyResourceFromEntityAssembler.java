package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import com.restock.platform.resource.domain.model.entities.Supply;
import com.restock.platform.resource.interfaces.rest.resources.CustomSupplyResource;
import com.restock.platform.resource.interfaces.rest.resources.SupplyResource;

public class CustomSupplyResourceFromEntityAssembler {

    public static CustomSupplyResource toResourceFromEntity(CustomSupply customSupply) {
        Supply supply = customSupply.getSupply();

        SupplyResource supplyResource = null;
        if (supply != null) {
            supplyResource = new SupplyResource(
                    supply.getId(),
                    supply.getName(),
                    supply.getDescription(),
                    supply.getPerishable(),
                    supply.getCategory()
            );
        }

        return new CustomSupplyResource(
                customSupply.getId(),
                supplyResource,
                customSupply.getDescription(),
                customSupply.getStockRange().minStock(),
                customSupply.getStockRange().maxStock(),
                customSupply.getPrice().amount(),
                customSupply.getUnitMeasurement().getUnitName(),
                customSupply.getUnitMeasurement().getUnitAbbreviaton(),
                customSupply.getPrice().currency().getCurrencyCode(),
                customSupply.getUserId()
        );
    }
}
