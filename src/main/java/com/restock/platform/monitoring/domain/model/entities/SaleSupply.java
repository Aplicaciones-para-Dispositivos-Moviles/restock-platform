package com.restock.platform.monitoring.domain.model.entities;
import lombok.Getter;
@Getter
public class SaleSupply {
    private Long supplyId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    protected SaleSupply() {
    }
    public SaleSupply(Long supplyId, Integer quantity, Double unitPrice) {
        if (supplyId == null || supplyId <= 0) {
            throw new IllegalArgumentException("Supply ID must be positive");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        this.supplyId = supplyId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }
}
