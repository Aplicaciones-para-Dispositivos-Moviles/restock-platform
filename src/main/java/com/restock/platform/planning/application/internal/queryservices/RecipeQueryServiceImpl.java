package com.restock.platform.planning.application.internal.queryservices;

import com.restock.platform.planning.domain.model.aggregates.Recipe;
import com.restock.platform.planning.domain.model.entities.RecipeSupply;
import com.restock.platform.planning.domain.model.queries.GetAllRecipesByUserIdQuery;
import com.restock.platform.planning.domain.model.queries.GetAllRecipesQuery;
import com.restock.platform.planning.domain.model.queries.GetRecipeByIdQuery;
import com.restock.platform.planning.domain.model.queries.GetRecipeSuppliesQuery;
import com.restock.platform.planning.domain.services.RecipeQueryService;
import com.restock.platform.planning.infrastructure.persistence.mongodb.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeQueryServiceImpl implements RecipeQueryService {

    private final RecipeRepository recipeRepository;

    public RecipeQueryServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<Recipe> handle(GetRecipeByIdQuery query) {
        return recipeRepository.findById(query.recipeId());
    }

    @Override
    public List<Recipe> handle(GetAllRecipesQuery query) {
        return recipeRepository.findAll();
    }

    @Override
    public List<RecipeSupply> handle(GetRecipeSuppliesQuery query) {
        return recipeRepository.findById(query.recipeId())
                .map(Recipe::getSupplies)
                .orElse(List.of());
    }

    @Override
    public List<Recipe> handle(GetAllRecipesByUserIdQuery query) {
        return recipeRepository.findRecipesByUserId(query.userId());
    }
}
