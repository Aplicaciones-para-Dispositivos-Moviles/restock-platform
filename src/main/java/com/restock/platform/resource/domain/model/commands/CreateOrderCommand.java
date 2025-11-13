package com.restock.platform.resource.domain.model.commands;

import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Command to create a new Order.
 * Represents the necessary data to register a new order from an admin restaurant to a supplier.
 */
public record CreateOrderCommand(
        Long adminRestaurantId,
        Long supplierId,
        List<OrderBatchItem> batchItems
) {}