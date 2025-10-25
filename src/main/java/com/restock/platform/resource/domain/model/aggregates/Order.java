package com.restock.platform.resource.domain.model.aggregates;

import com.restock.platform.resource.domain.model.commands.CreateOrderCommand;
import com.restock.platform.resource.domain.model.valueobjects.OrderBatchItem;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class Order extends AuditableAbstractAggregateRoot<Order> {

    @Getter
    private Long adminRestaurantId;

    @Getter
    private Long supplierId;

    @Getter
    private LocalDate date;

    @Getter
    private Integer requestedProductsCount = 0;

    @Getter
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Getter
    private boolean partiallyAccepted;

    @Getter
    private OrderToSupplierState state;

    @Getter
    private OrderToSupplierSituation situation;

    @Getter
    private List<OrderBatchItem> batchItems = new ArrayList<>();

    protected Order() { }

    public Order(CreateOrderCommand command) {
        this.adminRestaurantId = command.adminRestaurantId();
        this.supplierId = command.supplierId();
        this.date = LocalDate.now();
        this.partiallyAccepted = false;
        this.state = OrderToSupplierState.ON_HOLD;
        this.situation = OrderToSupplierSituation.PENDING;
    }

    public void recalculateTotals() {
        this.requestedProductsCount = batchItems.stream()
                .mapToInt(item -> item.getQuantity().intValue())
                .sum();

        this.totalPrice = batchItems.stream()
                .map(OrderBatchItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total price cannot be null or negative.");
        }
        this.totalPrice = totalPrice;
    }

    public void setRequestedProductsCount(Integer requestedProductsCount) {
        if (requestedProductsCount == null || requestedProductsCount < 0) {
            throw new IllegalArgumentException("Requested products count cannot be null or negative.");
        }
        this.requestedProductsCount = requestedProductsCount;
    }

    public Order update(OrderToSupplierState newState, OrderToSupplierSituation newSituation) {
        if (newState != null) this.state = newState;
        if (newSituation != null) this.situation = newSituation;
        return this;
    }

    public void addBatchItem(OrderBatchItem item) {
        if (item == null) throw new IllegalArgumentException("OrderBatchItem cannot be null");
        this.batchItems.add(item);
    }

    public void finalizeOrderTotals() {
        recalculateTotals();
    }
}
