package com.restock.platform.resource.domain.model.commands;

import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;

public record UpdateAlertCommand(
        Long alertId,
        OrderToSupplierState newState,
        OrderToSupplierSituation newSituation
) {
}
