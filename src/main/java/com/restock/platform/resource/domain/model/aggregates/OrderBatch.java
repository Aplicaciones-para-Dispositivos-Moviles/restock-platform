package com.restock.platform.resource.domain.model.aggregates;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Batch included in an Order to Supplier.
 */
@Document(collection = "order_batches")
public class OrderBatch extends AuditableAbstractAggregateRoot<OrderBatch> {

    @Getter
    private Long orderId;

    @Getter
    private Long batchId;

    @Getter
    private Integer quantity;

    @Getter
    private boolean accepted;

    protected OrderBatch() {
        // For JPA
    }

    public OrderBatch(Order order, Batch batch, Integer quantity, boolean accepted) {
        this.orderId = order != null ? order.getId() : null;
        this.batchId = batch != null ? batch.getId() : null;
        this.quantity = quantity;
        this.accepted = accepted;
    }

    public OrderBatch updateQuantity(Integer quantity) {
        if (quantity != null) this.quantity = quantity;
        return this;
    }

    public OrderBatch markAsAccepted() {
        this.accepted = true;
        return this;
    }
}
