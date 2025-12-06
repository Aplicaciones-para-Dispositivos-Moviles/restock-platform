package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.commands.UpdateOrderBatchItem;
import com.restock.platform.resource.domain.model.commands.UpdateOrderCommand;
import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;
import com.restock.platform.resource.interfaces.rest.resources.OrderBatchItemResource;
import com.restock.platform.resource.interfaces.rest.resources.UpdateOrderBatchItemResource;
import com.restock.platform.resource.interfaces.rest.resources.UpdateOrderResource;

import java.util.List;

public class UpdateOrderCommandFromResourceAssembler {

    public static UpdateOrderCommand toCommand(Long orderId, UpdateOrderResource resource) {

        List<UpdateOrderBatchItem> batchItems = resource.batchItems() == null
                ? List.of()
                : resource.batchItems().stream()
                .map(UpdateOrderCommandFromResourceAssembler::toUpdateOrderBatchItem)
                .toList();

        return new UpdateOrderCommand(
                orderId,
                resource.description(),
                resource.estimatedShipDate(),
                resource.estimatedShipTime(),
                batchItems
        );
    }

    private static UpdateOrderBatchItem toUpdateOrderBatchItem(
            UpdateOrderBatchItemResource resource
    ) {
        return new UpdateOrderBatchItem(
                resource.batchId(),
                resource.accept()
        );
    }
}