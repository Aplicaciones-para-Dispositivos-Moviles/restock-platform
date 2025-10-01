package com.restock.platform.resource.domain.model.valueobjects;

import java.util.Objects;

/**
 * Value Object representing a unit of measurement.
 */
public record UnitMeasurement(
        String unitName,
        String unitAbbreviaton
) {

    public UnitMeasurement {
        if (unitName == null || unitName.isBlank()) {
            throw new IllegalArgumentException("Unit name cannot be null or blank");
        }
        if (unitAbbreviaton == null || unitAbbreviaton.isBlank()) {
            throw new IllegalArgumentException("Unit abbreviation cannot be null or blank");
        }
    }

}
