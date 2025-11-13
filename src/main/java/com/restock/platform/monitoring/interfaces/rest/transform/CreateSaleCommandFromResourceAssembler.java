package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.interfaces.rest.resources.CreateSaleResource;

import java.util.stream.Collectors;

public class CreateSaleCommandFromResourceAssembler {
    public static CreateSaleCommand toCommandFromResource(CreateSaleResource resource) {
        var dishSelections = resource.dishSelections() != null
            ? resource.dishSelections().stream()
                .map(DishSelectionCommandFromResourceAssembler::toCommandFromResource)
                .collect(Collectors.toList())
            : null;

        var supplySelections = resource.supplySelections() != null
            ? resource.supplySelections().stream()
                .map(SupplySelectionCommandFromResourceAssembler::toCommandFromResource)
                .collect(Collectors.toList())
            : null;

        return new CreateSaleCommand(
            dishSelections,
            supplySelections,
            resource.subtotal(),
            resource.taxes(),
            resource.totalCost(),
            resource.userId()
        );
    }

    /**
     * Converts resource to command, overriding userId with authenticated user's ID
     * @param resource the CreateSaleResource
     * @param authenticatedUserId the ID of the authenticated user
     * @return CreateSaleCommand with authenticated user's ID
     */
    public static CreateSaleCommand toCommandFromResourceWithUserId(CreateSaleResource resource, Integer authenticatedUserId) {
        var dishSelections = resource.dishSelections() != null
            ? resource.dishSelections().stream()
                .map(DishSelectionCommandFromResourceAssembler::toCommandFromResource)
                .collect(Collectors.toList())
            : null;

        var supplySelections = resource.supplySelections() != null
            ? resource.supplySelections().stream()
                .map(SupplySelectionCommandFromResourceAssembler::toCommandFromResource)
                .collect(Collectors.toList())
            : null;

        return new CreateSaleCommand(
            dishSelections,
            supplySelections,
            resource.subtotal(),
            resource.taxes(),
            resource.totalCost(),
            authenticatedUserId  // Use authenticated user's ID instead of resource.userId()
        );
    }
}
