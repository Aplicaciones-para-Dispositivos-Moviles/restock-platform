package com.restock.platform.resource.domain.model.entities;


import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierState;
import com.restock.platform.shared.domain.model.entities.AuditableModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "alerts")
public class Alert extends AuditableModel {

    // Core details must be final to ensure immutability once the alert is created.
    private String message;
    private Long orderId;
    private LocalDate date;
    private Long supplierId;
    private Long adminRestaurantId;

    // The order situation (e.g., APPROVED, DECLINED) that triggered this specific alert event.
    private OrderToSupplierSituation situationAtAlert;
    private OrderToSupplierState stateAtAlert;


    protected Alert() {
    }

    /**
     * Creates an immutable alert record based on a key order situation change.
     * @param message User notification message.
     * @param orderId ID of the related order.
     * @param situation The specific situation (event trigger).
     */
    public Alert(String message, Long orderId, OrderToSupplierSituation situation, Long supplierId, Long adminRestaurantId) {
        this.message = message;
        this.orderId = orderId;
        this.situationAtAlert = situation;
        this.stateAtAlert = OrderToSupplierState.ON_HOLD;
        this.supplierId = supplierId;
        this.adminRestaurantId = adminRestaurantId;
        this.date = LocalDate.now();
    }

    public void updateStatus(OrderToSupplierState newState, OrderToSupplierSituation newSituation) {
        this.stateAtAlert = newState;
        this.situationAtAlert = newSituation;
    }


    /**
     * Checks if the alert requires immediate follow-up (e.g., rejection or cancellation).
     * @return True if the situation is DECLINED or CANCELLED.
     */
    public boolean requiresUrgentAttention() {
        return this.situationAtAlert == OrderToSupplierSituation.DECLINED ||
                this.situationAtAlert == OrderToSupplierSituation.CANCELLED;
    }

    /**
     * Provides a clean string for the UI from the enum name.
     * @return Formatted situation name (e.g., ON_HOLD -> ON HOLD).
     */
    public String getSituationDescription() {
        return this.situationAtAlert.name();
    }
}