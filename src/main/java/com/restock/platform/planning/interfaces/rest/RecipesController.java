package com.restock.platform.planning.interfaces.rest;

import com.restock.platform.planning.application.internal.commandservices.RecipeCommandServiceImpl;
import com.restock.platform.planning.application.internal.queryservices.RecipeQueryServiceImpl;
import com.restock.platform.planning.domain.model.commands.*;
import com.restock.platform.planning.domain.model.queries.*;
import com.restock.platform.planning.interfaces.rest.resources.*;
import com.restock.platform.planning.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recipes", description = "Recipe management endpoints")
public class RecipesController {

    private final RecipeCommandServiceImpl recipeCommandService;
    private final RecipeQueryServiceImpl recipeQueryService;

    public RecipesController(RecipeCommandServiceImpl recipeCommandService,
                             RecipeQueryServiceImpl recipeQueryService) {
        this.recipeCommandService = recipeCommandService;
        this.recipeQueryService = recipeQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<RecipeResource> createRecipe(@RequestBody CreateRecipeResource resource) {
        var createCommand = CreateRecipeCommandFromResourceAssembler.toCommandFromResource(resource);
        var recipeId = recipeCommandService.handle(createCommand);

        if (recipeId == null || recipeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetRecipeByIdQuery(recipeId);
        var recipeOptional = recipeQueryService.handle(query);

        if (recipeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var recipeResource = RecipeResourceFromEntityAssembler.toResourceFromEntity(recipeOptional.get());
        return new ResponseEntity<>(recipeResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all recipes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of recipes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No recipes found")
    })
    public ResponseEntity<List<RecipeResource>> getAllRecipes() {
        var recipes = recipeQueryService.handle(new GetAllRecipesQuery());
        var resources = recipes.stream()
                .map(RecipeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<RecipeResource> getRecipeById(@PathVariable Long id) {
        return recipeQueryService.handle(new GetRecipeByIdQuery(id))
                .map(RecipeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<RecipeResource> updateRecipe(@PathVariable Long id, @RequestBody UpdateRecipeResource resource) {
        var command = UpdateRecipeCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updated = recipeCommandService.handle(command);
        if (updated.isEmpty()) return ResponseEntity.notFound().build();
        var recipe = recipeQueryService.handle(new GetRecipeByIdQuery(id));
        if (recipe.isEmpty()) return ResponseEntity.notFound().build();
        var resourceResponse = RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe.get());
        return ResponseEntity.ok(resourceResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        try {
            recipeCommandService.handle(new DeleteRecipeCommand(id));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/supplies")
    @Operation(summary = "Add supplies to recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplies added successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Void> addSuppliesToRecipe(
            @PathVariable Long id,
            @RequestBody List<AddRecipeSupplyResource> supplies) {

        supplies.forEach(resource -> {
            var command = AddRecipeSupplyCommandFromResourceAssembler.toCommandFromResource(id, resource);
            recipeCommandService.handle(command);
        });

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{id}/supplies")
    @Operation(summary = "Get recipe supplies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplies retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<List<RecipeSupplyResource>> getSupplies(@PathVariable Long id) {
        var supplies = recipeQueryService.handle(new GetRecipeSuppliesQuery(id));
        var resources = supplies.stream()
                .map(s -> new RecipeSupplyResource(s.getId().getSupplyId(), s.getSupplyQuantity().supplyQuantity()))
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{recipeId}/supplies/{supplyId}")
    @Operation(summary = "Update recipe supply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply updated successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe or supply not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<RecipeResource> updateSupply(
            @PathVariable Long recipeId,
            @PathVariable Integer supplyId,
            @RequestBody AddRecipeSupplyResource resource) {
        var command = UpdateRecipeSupplyCommandFromResourceAssembler.toCommandFromResource(recipeId, supplyId, resource);
        var updated = recipeCommandService.handle(command);
        if (updated.isEmpty()) return ResponseEntity.notFound().build();
        var recipe = recipeQueryService.handle(new GetRecipeByIdQuery(recipeId));
        if (recipe.isEmpty()) return ResponseEntity.notFound().build();
        var resourceResponse = RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe.get());
        return ResponseEntity.ok(resourceResponse);
    }

    @DeleteMapping("/{recipeId}/supplies/{supplyId}")
    @Operation(summary = "Delete recipe supply")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe or supply not found")
    })
    public ResponseEntity<RecipeResource> deleteSupply(
            @PathVariable Long recipeId,
            @PathVariable Integer supplyId) {
        var updated = recipeCommandService.handle(new DeleteRecipeSupplyCommand(recipeId, supplyId));
        if (updated.isEmpty()) return ResponseEntity.notFound().build();
        var recipe = recipeQueryService.handle(new GetRecipeByIdQuery(recipeId));
        if (recipe.isEmpty()) return ResponseEntity.notFound().build();
        var resourceResponse = RecipeResourceFromEntityAssembler.toResourceFromEntity(recipe.get());
        return ResponseEntity.ok(resourceResponse);
    }

    @GetMapping("/{userId}/recipes")
    @Operation(summary = "Get All Recipes of User Serg ")
    @ApiResponses( value= {
            @ApiResponse(responseCode = "201", description = "Supplies added successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<List<RecipeResource>> getRecipesOfUser(@PathVariable Integer userId){
        var getAllRecipesByUserIdQuery = new GetAllRecipesByUserIdQuery(userId);
        var recipes = recipeQueryService.handle(getAllRecipesByUserIdQuery);
        var recipesResources = recipes.stream().map(RecipeResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(recipesResources);
    }

}