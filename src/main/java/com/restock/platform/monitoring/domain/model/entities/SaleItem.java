package com.restock.platform.monitoring.domain.model.entities;

import com.restock.platform.monitoring.domain.model.commands.CreateSaleItemCommand;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleItemId;
import lombok.Getter;

@Getter
public class SaleItem {
    private SaleItemId saleItemId;
    private Long recipeId;
    private Integer quantity;
    private Double subTotalPrice;

    protected SaleItem() {
    }

    public SaleItem(CreateSaleItemCommand command){
        this.saleItemId = new SaleItemId();
        this.recipeId = command.recipeId();
        this.quantity = command.quantity();
        this.subTotalPrice = command.price();
    }

    public SaleItem(Long saleId, Long recipeId, Integer quantity, Double subTotalPrice){
        this.saleItemId = new SaleItemId();
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.subTotalPrice = subTotalPrice;
    }

    public String saleItemIdToString(){
        return this.saleItemId.saleCodeId();
    }
}
