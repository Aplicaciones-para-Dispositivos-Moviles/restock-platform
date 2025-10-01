package com.restock.platform.planning.domain.model.valueobjects;

public record RecipeImageURL(String imageUrl) {
    public RecipeImageURL {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Image Url must not be null or blank");
        }
    }
}
