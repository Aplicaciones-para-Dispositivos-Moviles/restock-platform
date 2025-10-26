package com.restock.platform.monitoring.domain.model.aggregate;

import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.domain.model.entities.SaleItem;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleCode;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Document(collection = "sales")
public class Sale extends AuditableAbstractAggregateRoot<Sale> {
    private SaleCode saleCode;
    private Integer userId;
    private Double totalPrice;
    private List<SaleItem> items = new ArrayList<>();

    protected Sale() { }


    public Sale(Integer userId) {
        this.saleCode = new SaleCode();
        this.totalPrice = 0.0;
        this.userId = userId;
    }

    public Sale(CreateSaleCommand command){
        this.saleCode = new SaleCode();
        this.totalPrice = 0.0;
        this.userId = command.userId();
    }

    public void addItem(Long saleId, Long recipeId, Integer quantity, Double subTotalPrice){
        var existing = items.stream()
                .filter(i -> i.getRecipeId().equals(recipeId))
                .findFirst();
        if (existing.isPresent()){
            throw new IllegalArgumentException("Item already exists in sale");
        }
        var item = new SaleItem(saleId, recipeId, quantity, subTotalPrice);
        items.add(item);
        recalculateTotalPrice();

    }

    private void recalculateTotalPrice() {
        this.totalPrice = (items == null || items.isEmpty())
                ? 0.0
                : items.stream()
                .map(SaleItem::getSubTotalPrice)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

}
