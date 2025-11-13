package com.restock.platform.monitoring.interfaces.rest.resources;

public record SaleResponseResource(
    boolean success,
    SaleResource data,
    String message
) {
}

