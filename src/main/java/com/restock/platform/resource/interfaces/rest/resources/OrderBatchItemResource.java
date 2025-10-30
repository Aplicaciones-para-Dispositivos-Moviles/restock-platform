package com.restock.platform.resource.interfaces.rest.resources;

public record OrderBatchItemResource(
        Long batchId,
        double quantity,
        boolean accept,
        BatchResource batch
) {
}
