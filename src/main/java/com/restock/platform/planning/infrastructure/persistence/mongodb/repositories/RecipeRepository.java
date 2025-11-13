package com.restock.platform.planning.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.planning.domain.model.aggregates.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface RecipeRepository extends MongoRepository<Recipe, Long> {

    List<Recipe> findByUserId(Integer userId);

    List<Recipe> findRecipesByUserId(Integer userId);
}
