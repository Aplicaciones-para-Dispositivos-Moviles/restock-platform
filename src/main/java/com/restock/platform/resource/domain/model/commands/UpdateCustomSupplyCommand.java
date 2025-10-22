package com.restock.platform.resource.domain.model.commands;

import com.restock.platform.resource.domain.model.valueobjects.StockRange;
import com.restock.platform.resource.domain.model.valueobjects.Price;
import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;

public record UpdateCustomSupplyCommand(
        Long supplyId,
        String description,
        StockRange stockRange,
        Price price,
        UnitMeasurement unitMeasurement
) {
    public UpdateCustomSupplyCommand {
        if (supplyId == null || supplyId <= 0)
            throw new IllegalArgumentException("Supply ID must be a positive number.");

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description must not be null or blank.");

        if (stockRange == null)
            throw new IllegalArgumentException("Stock range must not be null.");

        if (price == null)
            throw new IllegalArgumentException("Price must not be null.");

        if (unitMeasurement == null)
            throw new IllegalArgumentException("Unit measurement must not be null.");
    }
}
