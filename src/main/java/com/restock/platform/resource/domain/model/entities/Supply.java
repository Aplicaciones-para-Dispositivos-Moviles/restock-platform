package com.restock.platform.resource.domain.model.entities;

import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;
import com.restock.platform.shared.domain.model.entities.AuditableModel;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

/*
        "name": "Pollo Entero",
        "description": "Pollo fresco sin vísceras",
        "perishable": true,
        "unitName": "Unidad",
        "unitAbbreviation": "u",
        "category": "Carnes"
 */
@Document(collection = "supplies")
public class Supply extends AuditableModel {

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private Boolean perishable;

    @Getter
    private String category;

    protected Supply() {
    }

    public Supply(String name, String description, Boolean perishable, String category) {
        this.name = name;
        this.description = description;
        this.perishable = perishable;
        this.category = category;
    }
}
