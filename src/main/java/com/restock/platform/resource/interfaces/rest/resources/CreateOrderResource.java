package com.restock.platform.resource.interfaces.rest.resources;

import java.util.List;

public record CreateOrderResource(
        Long adminRestaurantId,
        Long supplierId,
        List<AssignedBatchResource> batches
) {}
