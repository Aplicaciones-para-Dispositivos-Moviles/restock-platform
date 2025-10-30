package com.restock.platform.planning.interfaces.acl;

import com.restock.platform.planning.domain.model.valueobjects.RecipePrice;

public interface PlanningContextFacade {

    Long fetchRecipeByRecipeId(Long recipeId);
    Double fetchRecipePriceByRecipeId(Long recipeId);

}
