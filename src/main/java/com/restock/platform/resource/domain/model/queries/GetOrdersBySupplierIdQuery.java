package com.restock.platform.resource.domain.model.queries;

public record GetOrdersBySupplierIdQuery(Long supplierId) {
    public GetOrdersBySupplierIdQuery {
        if (supplierId == null || supplierId <= 0) {
            throw new IllegalArgumentException("Supplier ID must be a positive number");
        }
    }
}
