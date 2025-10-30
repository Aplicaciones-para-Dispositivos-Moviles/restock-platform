package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.entities.Supply;
import com.restock.platform.resource.interfaces.rest.resources.SupplyResource;

public class SupplyResourceFromEntityAssembler {
    public static SupplyResource toResourceFromEntity(Supply supply) {
        return new SupplyResource(
                supply.getId(),
                supply.getName(),
                supply.getDescription(),
                supply.getPerishable(),
                supply.getCategory()
        );
    }
}
