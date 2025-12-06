package com.restock.platform.resource.interfaces.rest.resources;

import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record OrderResource(
        Long id,
        Long adminRestaurantId,
        Long supplierId,
        LocalDate date,
        Integer requestedProductsCount,
        BigDecimal totalPrice,
        boolean partiallyAccepted,
        OrderToSupplierState state,
        OrderToSupplierSituation situation,
        List<OrderBatchItemResource> batchItems,
        String description,
        LocalDate estimatedShipDate,
        LocalTime estimatedShipTime
) {
}
