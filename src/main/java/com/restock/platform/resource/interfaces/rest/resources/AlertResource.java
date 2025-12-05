package com.restock.platform.resource.interfaces.rest.resources;

public record AlertResource(
        Long id,
        String message,
        Long orderId,
        String orderSituation,
        Long supplierId,
        Long recipientId
) {}