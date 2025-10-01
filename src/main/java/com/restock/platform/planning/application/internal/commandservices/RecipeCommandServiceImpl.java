package com.restock.platform.planning.application.internal.commandservices;

import com.restock.platform.planning.domain.model.aggregates.Recipe;
import com.restock.platform.planning.domain.model.commands.*;
import com.restock.platform.planning.domain.model.entities.RecipeSupply;
import com.restock.platform.planning.domain.model.valueobjects.*;
import com.restock.platform.planning.domain.services.RecipeCommandService;
import com.restock.platform.planning.infrastructure.persistence.mongodb.repositories.RecipeRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeRepository recipeRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public RecipeCommandServiceImpl(RecipeRepository recipeRepository,
                                    SequenceGeneratorService sequenceGeneratorService) {
        this.recipeRepository = recipeRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public Long handle(CreateRecipeCommand command) {
        var recipe = new Recipe(
                command.name(),
                command.description(),
                new RecipeImageURL(command.imageUrl()),
                new RecipePrice(command.price()),
                command.userId()
        );

        var id = sequenceGeneratorService.generateSequence("recipes_sequence");
        recipe.setId(id);

        try {
            recipeRepository.save(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Error saving recipe: " + e.getMessage(), e);
        }

        return recipe.getId();
    }


    @Override
    public Optional<Recipe> handle(UpdateRecipeCommand command) {
        var recipe = recipeRepository.findById(command.recipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + command.recipeId()));

        recipe.update(
                command.name(),
                command.description(),
                new RecipeImageURL(command.imageUrl()),
                new RecipePrice(command.price())
        );

        recipeRepository.save(recipe);
        return Optional.of(recipe);
    }

    @Override
    public void handle(DeleteRecipeCommand command) {
        if (!recipeRepository.existsById(command.recipeId())) {
            throw new IllegalArgumentException("Recipe not found with id: " + command.recipeId());
        }
        recipeRepository.deleteById(command.recipeId());
    }

    @Override
    public Optional<Recipe> handle(AddRecipeSupplyCommand command) {
        var recipe = recipeRepository.findById(command.recipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + command.recipeId()));

        recipe.addSupply(new RecipeId(command.recipeId()), new CatalogSupplyId(command.supplyId()), new RecipeSupplyQuantity(command.quantity()));
        recipeRepository.save(recipe);
        return Optional.of(recipe);
    }

    @Override
    public Optional<Recipe> handle(UpdateRecipeSupplyCommand command) {
        var recipe = recipeRepository.findById(command.recipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + command.recipeId()));

        recipe.updateSupply(new CatalogSupplyId(command.supplyId()), new RecipeSupplyQuantity(command.quantity()));
        recipeRepository.save(recipe);
        return Optional.of(recipe);
    }

    @Override
    public Optional<Recipe> handle(DeleteRecipeSupplyCommand command) {
        var recipe = recipeRepository.findById(command.recipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + command.recipeId()));

        recipe.removeSupply(new CatalogSupplyId(command.supplyId()));
        recipeRepository.save(recipe);
        return Optional.of(recipe);
    }
}
