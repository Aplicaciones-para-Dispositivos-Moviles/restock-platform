package com.restock.platform.monitoring.domain.model.valueobjects;

import java.util.UUID;

public record SaleCode(String saleCodeId) {
    public SaleCode(){
        this(UUID.randomUUID().toString());
    }
    public SaleCode {
        if (saleCodeId == null || saleCodeId.isBlank())
            throw new IllegalArgumentException("Sale code ID must not be null or blank.");
    }
}
