package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.restock.platform.resource.domain.model.aggregates.*;
import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;
import com.restock.platform.resource.interfaces.rest.resources.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResourceFromEntityAssembler {

    public static OrderResource toResourceFromEntity(Order order) {
        List<OrderBatchItemResource> batchItemResources = order.getBatchItems() == null
                ? List.of()
                : order.getBatchItems().stream()
                .map(OrderResourceFromEntityAssembler::toOrderBatchItemResource)
                .collect(Collectors.toList());

        return new OrderResource(
                order.getId(),
                order.getAdminRestaurantId(),
                order.getSupplierId(),
                order.getDate(),
                order.getRequestedProductsCount(),
                order.getTotalPrice(),
                order.isPartiallyAccepted(),
                order.getState(),
                order.getSituation(),
                batchItemResources
        );
    }

    private static OrderBatchItemResource toOrderBatchItemResource(OrderBatchItem item) {
        return new OrderBatchItemResource(
                item.getBatchId(),
                item.getQuantity(),
                item.isAccept(),
                toBatchResource(item.getBatch())
        );
    }

    private static BatchResource toBatchResource(Batch batch) {
        if (batch == null) return null;

        return new BatchResource(
                batch.getId(),
                batch.getUserId(),
                batch.getUserRoleId(),
                batch.getCustomSupplyId(),
                batch.getStock(),
                batch.getExpirationDate(),
                CustomSupplyResourceFromEntityAssembler.toResourceFromEntity(batch.getCustomSupply())
        );
    }
}
