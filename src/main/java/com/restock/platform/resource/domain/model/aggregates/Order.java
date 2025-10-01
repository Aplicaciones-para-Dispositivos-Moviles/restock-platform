package com.restock.platform.resource.domain.model.aggregates;

import com.restock.platform.resource.domain.model.commands.CreateOrderCommand;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Order aggregate root.
 *
 * Represents a purchase order from an admin restaurant to a supplier.
 */
@Document(collection = "orders")
public class Order extends AuditableAbstractAggregateRoot<Order> {

    @Getter
    private Long adminRestaurantId;

    @Getter
    private Long supplierId;

    @Getter
    private LocalDate date;

    @Getter
    private Integer requestedProductsCount;

    @Getter
    private BigDecimal totalPrice;

    @Getter
    private boolean partiallyAccepted;

    @Getter
    private OrderToSupplierState state;

    @Getter
    private OrderToSupplierSituation situation;

    protected Order() {
        // For JPA
    }

    /**
     * Full constructor for an Order.
     */
    public Order(Long adminRestaurantId, Long supplierId, LocalDate date, Integer requestedProductsCount,
                 BigDecimal totalPrice, boolean partiallyAccepted,
                 OrderToSupplierState state, OrderToSupplierSituation situation) {
        this.adminRestaurantId = adminRestaurantId;
        this.supplierId = supplierId;
        this.date = date;
        this.requestedProductsCount = requestedProductsCount;
        this.totalPrice = totalPrice;
        this.partiallyAccepted = partiallyAccepted;
        this.state = state;
        this.situation = situation;
    }

    /**
     * Constructs an Order from a CreateOrderCommand.
     */
    public Order(CreateOrderCommand command) {
        if (command.totalPrice() == null) {
            throw new IllegalArgumentException("totalPrice cannot be null");
        }
        if (command.requestedProductsCount() == null) {
            throw new IllegalArgumentException("requestedProductsCount cannot be null");
        }

        this.adminRestaurantId = command.adminRestaurantId();
        this.supplierId = command.supplierId();
        this.date = command.date();
        this.requestedProductsCount = command.requestedProductsCount();
        this.totalPrice = command.totalPrice();
        this.partiallyAccepted = false;
        this.state = OrderToSupplierState.ON_HOLD;
        this.situation = OrderToSupplierSituation.PENDING;
    }


    /**
     * Updates the state and situation of the order.
     */
    public Order update(OrderToSupplierState newState, OrderToSupplierSituation newSituation) {
        if (newState != null) this.state = newState;
        if (newSituation != null) this.situation = newSituation;
        return this;
    }

    public Order updateState(OrderToSupplierState newState, OrderToSupplierSituation newSituation) {
        if (newState != null) this.state = newState;
        if (newSituation != null) this.situation = newSituation;
        return this;
    }

}
