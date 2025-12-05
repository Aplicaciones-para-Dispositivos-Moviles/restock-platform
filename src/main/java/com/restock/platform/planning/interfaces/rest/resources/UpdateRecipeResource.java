package com.restock.platform.planning.interfaces.rest.resources;

public record UpdateRecipeResource(
        String name,
        String description,
        String imageUrl,
        Double price
) {
    public UpdateRecipeResource {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or blank");
        // imageUrl is now optional - no validation required
        if (price == null || price < 0)
            throw new IllegalArgumentException("Total price must be a non-negative number");
    }
}