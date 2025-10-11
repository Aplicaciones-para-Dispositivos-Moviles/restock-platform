package com.restock.platform.planning.domain.model.valueobjects;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RecipeSupplyId implements Serializable {

    private Long recipeId;

    private Integer supplyId;

    protected RecipeSupplyId() {}

    public RecipeSupplyId(RecipeId recipeId, CatalogSupplyId supplyId) {
        this.recipeId = recipeId.recipeId();
        this.supplyId = supplyId.value();
    }

    // equals y hashCode obligatorios
}