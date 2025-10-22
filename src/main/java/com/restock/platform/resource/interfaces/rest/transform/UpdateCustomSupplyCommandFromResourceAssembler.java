package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.commands.UpdateCustomSupplyCommand;
import com.restock.platform.resource.domain.model.valueobjects.Price;
import com.restock.platform.resource.domain.model.valueobjects.StockRange;
import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;
import com.restock.platform.resource.interfaces.rest.resources.UpdateCustomSupplyResource;

import java.util.Currency;

public class UpdateCustomSupplyCommandFromResourceAssembler {
    public static UpdateCustomSupplyCommand toCommandFromResource(Long id, UpdateCustomSupplyResource resource) {
        return new UpdateCustomSupplyCommand(
                id,
                resource.supplyId(),
                resource.description(),
                new StockRange(resource.minStock(), resource.maxStock()),
                new Price(resource.price(), Currency.getInstance("USD")),
                new UnitMeasurement(resource.unitName(), resource.unitAbbreviaton())
        );
    }
}
