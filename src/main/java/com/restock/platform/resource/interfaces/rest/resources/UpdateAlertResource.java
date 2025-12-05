package com.restock.platform.resource.interfaces.rest.resources;

import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;

public record UpdateAlertResource(
        Long alertId,
        OrderToSupplierState newState,
        OrderToSupplierSituation newSituation
) {
}
