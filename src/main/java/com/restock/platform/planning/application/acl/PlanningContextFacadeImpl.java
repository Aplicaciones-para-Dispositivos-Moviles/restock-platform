package com.restock.platform.planning.application.acl;

import com.restock.platform.planning.domain.model.queries.GetRecipeByIdQuery;
import com.restock.platform.planning.domain.model.valueobjects.RecipeId;
import com.restock.platform.planning.domain.services.RecipeCommandService;
import com.restock.platform.planning.domain.services.RecipeQueryService;
import com.restock.platform.planning.interfaces.acl.PlanningContextFacade;
import com.restock.platform.profile.domain.services.ProfileCommandService;
import com.restock.platform.profile.domain.services.ProfileQueryService;
import org.springframework.stereotype.Service;

@Service
public class PlanningContextFacadeImpl implements PlanningContextFacade {
    private final RecipeQueryService recipeQueryService;
    private final RecipeCommandService recipeCommandService;

    public PlanningContextFacadeImpl(RecipeQueryService recipeQueryService, RecipeCommandService recipeCommandService) {
        this.recipeCommandService = recipeCommandService;
        this.recipeQueryService = recipeQueryService;
    }


    @Override
    public Long fetchRecipeByRecipeId(Long recipeId) {
        var getRecipeByIdQuery = new GetRecipeByIdQuery(recipeId);
        var recipe = recipeQueryService.handle(getRecipeByIdQuery);
        return recipe.isEmpty() ? 0L: recipe.get().getId();
    }

    @Override
    public Double fetchRecipePriceByRecipeId(Long recipeId) {
        var getRecipeByIdQuery = new GetRecipeByIdQuery(recipeId);
        var recipe = recipeQueryService.handle(getRecipeByIdQuery);
        return recipe.isEmpty() ? 0.0: recipe.get().getPrice().price();
    }
}
