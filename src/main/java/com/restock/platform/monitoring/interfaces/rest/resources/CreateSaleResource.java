package com.restock.platform.monitoring.interfaces.rest.resources;

//Lo que necesitamos para crear una venta
public record CreateSaleResource(
        int userId
) {
    public CreateSaleResource {
        if (userId <= 0)
            throw new IllegalArgumentException("User ID must be a positive number");
    }
}
