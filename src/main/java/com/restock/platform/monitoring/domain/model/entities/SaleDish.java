package com.restock.platform.monitoring.domain.model.entities;
import lombok.Getter;
@Getter
public class SaleDish {
    private Long dishId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    protected SaleDish() {
    }
    public SaleDish(Long dishId, Integer quantity, Double unitPrice) {
        if (dishId == null || dishId <= 0) {
            throw new IllegalArgumentException("Dish ID must be positive");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        this.dishId = dishId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }
}
