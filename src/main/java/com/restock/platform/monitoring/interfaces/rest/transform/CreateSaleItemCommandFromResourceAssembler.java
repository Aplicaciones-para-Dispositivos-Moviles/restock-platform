package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.commands.CreateSaleItemCommand;
import com.restock.platform.monitoring.interfaces.rest.resources.CreateSaleItemResource;

public class CreateSaleItemCommandFromResourceAssembler {
    public static CreateSaleItemCommand toCommandFromResource(CreateSaleItemResource resource) {
        return new CreateSaleItemCommand(
                resource.recipeId(),
                resource.quantity(),
                resource.price()
        );
    }
}
