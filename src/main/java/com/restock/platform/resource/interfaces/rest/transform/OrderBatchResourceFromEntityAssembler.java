package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.aggregates.OrderBatch;
import com.restock.platform.resource.interfaces.rest.resources.OrderBatchResource;

public class OrderBatchResourceFromEntityAssembler {
    public static OrderBatchResource toResourceFromEntity(OrderBatch orderBatch) {
        return new OrderBatchResource(
                orderBatch.getId(),
                orderBatch.getOrderId(),
                orderBatch.getBatchId(),
                orderBatch.getQuantity(),
                orderBatch.isAccepted()
        );
    }
}
