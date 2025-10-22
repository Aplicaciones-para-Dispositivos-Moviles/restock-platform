package com.restock.platform.resource.interfaces.rest.resources;

public record UpdateCustomSupplyResource(
        Long supplyId,
        String description,
        int minStock,
        int maxStock,
        double price,
        String unitName,
        String unitAbbreviaton

) {
    public UpdateCustomSupplyResource {
        if (supplyId == null)
            throw new IllegalArgumentException("Supply Id is required");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description is required");
        if (minStock < 0)
            throw new IllegalArgumentException("Min stock must be non-negative");
        if (maxStock < minStock)
            throw new IllegalArgumentException("Max stock must be greater than or equal to min stock");
        if (price < 0)
            throw new IllegalArgumentException("Price must be non-negative");

        if (unitAbbreviaton == null || unitAbbreviaton.isBlank()) {
            throw new IllegalArgumentException("Unit abbreviation cannot be null or blank");
        }
        if (unitName == null || unitName.isBlank()) {
            throw new IllegalArgumentException("Unit name cannot be null or blank");
        }
    }
}
