package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.commands.AddSaleItemToSaleCommand;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleItemId;
import com.restock.platform.monitoring.interfaces.rest.resources.AddSaleItemToSaleResource;

public class AddSaleItemToSaleCommandFromResourceAssembler {
    public static AddSaleItemToSaleCommand toCommandFromResource(Long saleId, AddSaleItemToSaleResource resource) {
        return new AddSaleItemToSaleCommand(
                saleId,
                resource.recipeId(),
                resource.quantity()
        );
    }

}
