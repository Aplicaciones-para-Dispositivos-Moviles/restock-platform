package com.restock.platform.resource.domain.model.commands;

import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;

/*
        "name": "Pollo Entero",
        "description": "Pollo fresco sin vísceras",
        "perishable": true,
        "unitName": "Unidad",
        "unitAbbreviation": "u",
        "category": "Carnes"
 */
public record CreateSupplyCommand(
        String name, String description, Boolean perishable, String category
) {
}
