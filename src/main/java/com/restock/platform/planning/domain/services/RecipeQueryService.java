package com.restock.platform.planning.domain.services;

import com.restock.platform.planning.domain.model.aggregates.Recipe;
import com.restock.platform.planning.domain.model.entities.RecipeSupply;
import com.restock.platform.planning.domain.model.queries.GetAllRecipesQuery;
import com.restock.platform.planning.domain.model.queries.GetRecipeByIdQuery;
import com.restock.platform.planning.domain.model.queries.GetRecipeSuppliesQuery;

import java.util.List;
import java.util.Optional;

public interface RecipeQueryService {
    Optional<Recipe> handle(GetRecipeByIdQuery query);

    List<Recipe> handle(GetAllRecipesQuery query);

    List<RecipeSupply> handle(GetRecipeSuppliesQuery query);

}