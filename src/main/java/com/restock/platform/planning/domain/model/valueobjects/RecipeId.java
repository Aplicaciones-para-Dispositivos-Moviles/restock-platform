package com.restock.platform.planning.domain.model.valueobjects;

public record RecipeId(Long recipeId) {
    public RecipeId {
        if (recipeId == null || recipeId <= 0) {
            throw new IllegalArgumentException("RecipeId must be a positive number.");
        }
    }
}