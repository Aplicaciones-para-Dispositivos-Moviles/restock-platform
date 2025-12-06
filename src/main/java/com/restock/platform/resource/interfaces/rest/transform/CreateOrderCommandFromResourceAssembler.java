package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.commands.CreateOrderCommand;
import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;
import com.restock.platform.resource.interfaces.rest.resources.CreateOrderResource;

import java.util.List;
import java.util.stream.Collectors;

public class CreateOrderCommandFromResourceAssembler {
    public static CreateOrderCommand toCommandFromResource(CreateOrderResource resource) {
        List<OrderBatchItem> batchItems = resource.batches() == null ? List.of()
                : resource.batches().stream()
                .map(b -> new OrderBatchItem(b.batchId(), b.quantity(), b.accept()))
                .collect(Collectors.toList());

        return new CreateOrderCommand(
                resource.adminRestaurantId(),
                resource.supplierId(),
                batchItems,
                resource.description(),
                resource.estimatedShipDate(),
                resource.estimatedShipTime()
        );
    }
}
