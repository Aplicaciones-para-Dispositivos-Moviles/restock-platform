package com.restock.platform.planning.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.planning.domain.model.aggregates.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RecipeRepository extends MongoRepository<Recipe, Long> {

}
