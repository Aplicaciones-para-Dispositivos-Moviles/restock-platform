package com.restock.platform.resource.interfaces.rest.resources;

public record AssignedBatchResource(
        Long batchId,
        Double quantity,
        Boolean accept
) {}
