package com.restock.platform.monitoring.domain.model.aggregate;

import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.domain.model.entities.SaleDish;
import com.restock.platform.monitoring.domain.model.entities.SaleSupply;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleNumber;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleStatus;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Document(collection = "sales")
public class Sale extends AuditableAbstractAggregateRoot<Sale> {
    private SaleNumber saleNumber;
    private Double totalCost;
    private Double subtotal;
    private Double taxes;
    private Date registeredDate;
    private Integer userId;
    private SaleStatus status;
    private final List<SaleDish> dishSelections = new ArrayList<>();
    private final List<SaleSupply> supplySelections = new ArrayList<>();

    protected Sale() {
    }

    public Sale(CreateSaleCommand command, Long sequence) {
        this.saleNumber = SaleNumber.generateFromSequence(sequence);
        this.subtotal = command.subtotal();
        this.taxes = command.taxes();
        this.totalCost = command.totalCost();
        this.userId = command.userId();
        this.status = SaleStatus.COMPLETED;
        this.registeredDate = new Date();

        // Add dishes
        if (command.dishSelections() != null) {
            command.dishSelections().forEach(dish ->
                this.dishSelections.add(new SaleDish(dish.dishId(), dish.quantity(), dish.unitPrice()))
            );
        }

        // Add supplies
        if (command.supplySelections() != null) {
            command.supplySelections().forEach(supply ->
                this.supplySelections.add(new SaleSupply(supply.supplyId(), supply.quantity(), supply.unitPrice()))
            );
        }

        validateCalculations();
    }

    private void validateCalculations() {
        double calculatedSubtotal = calculateSubtotal();
        double tolerance = 0.01; // Tolerancia para errores de redondeo

        if (Math.abs(this.subtotal - calculatedSubtotal) > tolerance) {
            throw new IllegalArgumentException(
                String.format("Subtotal mismatch. Expected: %.2f, Calculated: %.2f",
                    this.subtotal, calculatedSubtotal)
            );
        }

        double calculatedTotal = this.subtotal + this.taxes;
        if (Math.abs(this.totalCost - calculatedTotal) > tolerance) {
            throw new IllegalArgumentException(
                String.format("Total cost mismatch. Expected: %.2f, Calculated: %.2f",
                    this.totalCost, calculatedTotal)
            );
        }
    }

    private double calculateSubtotal() {
        double dishesSubtotal = dishSelections.stream()
            .mapToDouble(SaleDish::getSubtotal)
            .sum();

        double suppliesSubtotal = supplySelections.stream()
            .mapToDouble(SaleSupply::getSubtotal)
            .sum();

        return dishesSubtotal + suppliesSubtotal;
    }

    public void cancel() {
        if (this.status == SaleStatus.CANCELLED) {
            throw new IllegalStateException("Sale is already cancelled");
        }
        this.status = SaleStatus.CANCELLED;
    }

    public boolean isCompleted() {
        return this.status == SaleStatus.COMPLETED;
    }

    public boolean isCancelled() {
        return this.status == SaleStatus.CANCELLED;
    }
}
