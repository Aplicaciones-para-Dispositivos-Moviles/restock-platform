package com.restock.platform.resource.interfaces.rest.resources;

public record CreateCustomSupplyResource(
        Long supplyId,
        String description,
        int minStock,
        int maxStock,
        double price,
        Long userId,
        String unitName,
        String unitAbbreviaton
) {
    public CreateCustomSupplyResource {
        if (supplyId == null) {
            throw new IllegalArgumentException("supplyId cannot be null");
        }
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description is required");
        if (minStock < 0)
            throw new IllegalArgumentException("Min stock must be non-negative");
        if (maxStock < minStock)
            throw new IllegalArgumentException("Max stock must be greater than or equal to min stock");
        if (price < 0)
            throw new IllegalArgumentException("Price must be non-negative");
        if (userId == null)
            throw new IllegalArgumentException("User ID is required");
        if (unitName == null || unitName.isBlank()) {
            throw new IllegalArgumentException("Unit name cannot be null or blank");
        }
        if (unitAbbreviaton == null || unitAbbreviaton.isBlank()) {
            throw new IllegalArgumentException("Unit abbreviation cannot be null or blank");
        }
    }
}


