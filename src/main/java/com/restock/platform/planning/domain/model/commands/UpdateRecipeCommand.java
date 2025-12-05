package com.restock.platform.planning.domain.model.commands;

public record UpdateRecipeCommand(
        Long recipeId,
        String name,
        String description,
        String imageUrl,
        Double price
) {
    public UpdateRecipeCommand {
        if (recipeId == null || recipeId <= 0)
            throw new IllegalArgumentException("Recipe ID must be a positive number.");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or blank");
        // imageUrl is now optional - no validation required
        if (price == null || price < 0)
            throw new IllegalArgumentException("Price must be a non-negative number");
    }
}