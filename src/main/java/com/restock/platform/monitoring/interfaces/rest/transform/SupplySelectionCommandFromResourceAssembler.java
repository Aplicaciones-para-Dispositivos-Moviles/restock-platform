package com.restock.platform.monitoring.interfaces.rest.transform;
import com.restock.platform.monitoring.domain.model.commands.SupplySelectionCommand;
import com.restock.platform.monitoring.interfaces.rest.resources.SupplySelectionResource;
public class SupplySelectionCommandFromResourceAssembler {
    public static SupplySelectionCommand toCommandFromResource(SupplySelectionResource resource) {
        return new SupplySelectionCommand(
            resource.supplyId(),
            resource.quantity(),
            resource.unitPrice()
        );
    }
}
