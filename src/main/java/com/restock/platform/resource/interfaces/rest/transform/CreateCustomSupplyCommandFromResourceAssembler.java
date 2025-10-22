package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.commands.CreateCustomSupplyCommand;
import com.restock.platform.resource.domain.model.valueobjects.StockRange;
import com.restock.platform.resource.domain.model.valueobjects.Price;
import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;
import com.restock.platform.resource.interfaces.rest.resources.CreateCustomSupplyResource;

import java.util.Currency;

public class CreateCustomSupplyCommandFromResourceAssembler {
    public static CreateCustomSupplyCommand toCommandFromResource(CreateCustomSupplyResource resource) {
        return new CreateCustomSupplyCommand(
                resource.supplyId(),
                new StockRange(resource.minStock(), resource.maxStock()),
                new Price(resource.price(), Currency.getInstance("PEN")),
                resource.description(),
                resource.userId(),
                new UnitMeasurement(resource.unitName(), resource.unitAbbreviaton())
        );
    }
}

