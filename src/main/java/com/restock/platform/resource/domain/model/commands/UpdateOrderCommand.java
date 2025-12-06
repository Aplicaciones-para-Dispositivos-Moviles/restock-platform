package com.restock.platform.resource.domain.model.commands;

import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record UpdateOrderCommand(
        Long orderId,
        String description,
        LocalDate estimatedShipDate,
        LocalTime estimatedShipTime,
        List<UpdateOrderBatchItem> batchItems
) {}