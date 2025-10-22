package com.restock.platform.resource.domain.model.commands;

import com.restock.platform.resource.domain.model.valueobjects.Price;
import com.restock.platform.resource.domain.model.valueobjects.StockRange;
import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;

public record CreateCustomSupplyCommand(
        Long supplyId,     // ID de ReferenceSupply
        StockRange stockRange,       // Value Object con min/max stock
        Price price,                 // Value Object con amount y currency
        String description,          // Descripción personalizada
        Long userId,
        UnitMeasurement unitMeasurement// ID del usuario
) {
    public CreateCustomSupplyCommand {
        // Validación de referenceSupplyId
        if (supplyId == null || supplyId <= 0)
            throw new IllegalArgumentException("Supply ID must be a positive number");

        // Validación de stockRange
        if (stockRange == null)
            throw new IllegalArgumentException("Stock range cannot be null");

        // Validación de price
        if (price == null)
            throw new IllegalArgumentException("Price cannot be null");

        // Validación de descripción
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or blank");

        // Validación de userId
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("User ID must be a positive number");

        // Validación de stockRange
        if (unitMeasurement == null)
            throw new IllegalArgumentException("Unit measurement range cannot be null");
    }
}