package com.restock.platform.resource.domain.model.commands;

public record UpdateOrderBatchItem(
        Long batchId,
        Boolean accept
) {}