package com.restock.platform.monitoring.interfaces.rest.transform;

import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleCode;
import com.restock.platform.monitoring.interfaces.rest.resources.CreateSaleResource;

public class CreateSaleCommandFromResourceAssembler {
    public static CreateSaleCommand toCommandFromResource(CreateSaleResource resource) {
        return new CreateSaleCommand(
                resource.userId()
        );
    }
}
