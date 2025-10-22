package com.restock.platform.resource.interfaces.rest.resources;

public record CustomSupplyResource(
        Long id,
        SupplyResource supply,
        String description,
        int minStock,
        int maxStock,
        double price,
        String currencyCode,
        Long userId
) {}
