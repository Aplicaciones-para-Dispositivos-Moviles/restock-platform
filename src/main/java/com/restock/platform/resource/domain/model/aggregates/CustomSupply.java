package com.restock.platform.resource.domain.model.aggregates;

import com.restock.platform.resource.domain.model.commands.CreateCustomSupplyCommand;
import com.restock.platform.resource.domain.model.valueobjects.StockRange;
import com.restock.platform.resource.domain.model.valueobjects.Price;
import com.restock.platform.resource.domain.model.valueobjects.UnitMeasurement;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "custom_supplies")
public class CustomSupply extends AuditableAbstractAggregateRoot<CustomSupply> {

    @Getter
    private Long userId;

    @Getter
    private Long supplyId;

    @Getter
    private StockRange stockRange;

    @Getter
    private Price price;

    @Getter
    private String description;

    @Getter
    private UnitMeasurement unitMeasurement;

    protected CustomSupply() {
        // Para JPA
    }

    public CustomSupply(CreateCustomSupplyCommand command) {
        this.supplyId = command.supplyId();
        this.stockRange = command.stockRange();
        this.price = command.price();
        this.description = command.description();
        this.userId = command.userId();
    }

    public CustomSupply update(StockRange stockRange, Price price, String description , UnitMeasurement unitMeasurement) {
        this.stockRange = stockRange;
        this.price = price;
        this.description = description;
        this.unitMeasurement = unitMeasurement;
        return this;
    }
}