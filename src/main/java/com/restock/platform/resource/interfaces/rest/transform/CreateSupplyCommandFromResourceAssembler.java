package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.commands.CreateSupplyCommand;
import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;
import com.restock.platform.resource.interfaces.rest.resources.SupplyResource;

public class CreateSupplyCommandFromResourceAssembler {

    public static CreateSupplyCommand toCommandFromResource(SupplyResource resource) {
        return new CreateSupplyCommand(
                resource.name(),
                resource.description(),
                resource.perishable(),
                resource.category()
        );
    }
}
