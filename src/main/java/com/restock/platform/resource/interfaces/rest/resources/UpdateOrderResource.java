package com.restock.platform.resource.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record UpdateOrderResource(
        String description,
        LocalDate estimatedShipDate,
        LocalTime estimatedShipTime,
        List<UpdateOrderBatchItemResource> batchItems
) {}
