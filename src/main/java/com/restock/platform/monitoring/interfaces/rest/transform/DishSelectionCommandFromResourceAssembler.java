package com.restock.platform.monitoring.interfaces.rest.transform;
import com.restock.platform.monitoring.domain.model.commands.DishSelectionCommand;
import com.restock.platform.monitoring.interfaces.rest.resources.DishSelectionResource;
public class DishSelectionCommandFromResourceAssembler {
    public static DishSelectionCommand toCommandFromResource(DishSelectionResource resource) {
        return new DishSelectionCommand(
            resource.dishId(),
            resource.quantity(),
            resource.unitPrice()
        );
    }
}
