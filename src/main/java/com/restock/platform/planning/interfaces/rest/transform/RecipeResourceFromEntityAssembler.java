package com.restock.platform.planning.interfaces.rest.transform;

import com.restock.platform.planning.domain.model.aggregates.Recipe;
import com.restock.platform.planning.interfaces.rest.resources.RecipeResource;
import com.restock.platform.planning.interfaces.rest.resources.RecipeSupplyResource;

import java.util.List;

/**
 * Converts a Recipe entity to a RecipeResource.
 * This class is responsible for transforming the Recipe aggregate into a RESTful resource representation.
 */
public class RecipeResourceFromEntityAssembler {
    public static RecipeResource toResourceFromEntity(Recipe recipe) {
        List<RecipeSupplyResource> supplies = recipe.getSupplies().stream()
                .map(supply -> new RecipeSupplyResource(
                        supply.getId().getSupplyId(),
                        supply.getSupplyQuantity().supplyQuantity()
                )).toList();

        String imageUrl = recipe.getImageUrl() != null ? recipe.getImageUrl().imageUrl() : null;

        return new RecipeResource(
                recipe.getRecipeId().recipeId(),
                recipe.getName(),
                recipe.getDescription(),
                imageUrl,
                recipe.getPrice().price(),
                recipe.getUserId(),
                supplies
        );
    }
}

