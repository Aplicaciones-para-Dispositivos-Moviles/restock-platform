package com.restock.platform.monitoring.interfaces.rest.resources;
public record SupplySelectionResource(
    Long supplyId,
    Integer quantity,
    Double unitPrice
) {
}
