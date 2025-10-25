package com.restock.platform.resource.domain.model.valueobjects;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderBatchItem {

    @Field("batch_id")
    private Long batchId;

    @Field("quantity")
    private Double quantity;

    @Field("accept")
    private boolean accept;

    private transient Batch batch;

    protected OrderBatchItem() {}

    public OrderBatchItem(Long batchId, Double quantity, boolean accept) {
        this.batchId = batchId;
        this.quantity = quantity;
        this.accept = accept;
    }

    public BigDecimal getTotalPrice() {
        if (batch == null || batch.getCustomSupply() == null) {
            return BigDecimal.ZERO;
        }

        double price = batch.getCustomSupply().getPrice().amount();
        return BigDecimal.valueOf(price * (quantity != null ? quantity : 0));
    }
}
