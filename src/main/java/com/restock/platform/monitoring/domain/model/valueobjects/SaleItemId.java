package com.restock.platform.monitoring.domain.model.valueobjects;

import java.util.UUID;

public record SaleItemId(String saleCodeId) {
    public SaleItemId() {
        this(UUID.randomUUID().toString());
    }
    public SaleItemId {
        if (saleCodeId == null || saleCodeId.isBlank())
            throw new IllegalArgumentException("Sale code ID must not be null or blank.");
    }
}
