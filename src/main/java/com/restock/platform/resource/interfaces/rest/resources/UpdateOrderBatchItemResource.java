package com.restock.platform.resource.interfaces.rest.resources;

public record UpdateOrderBatchItemResource(
        Long batchId,
        Boolean accept
) {}