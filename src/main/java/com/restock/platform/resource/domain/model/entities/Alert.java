package com.restock.platform.resource.domain.model.entities;


import com.restock.platform.resource.domain.model.valueobjects.OrderToSupplierSituation;
import com.restock.platform.shared.domain.model.entities.AuditableModel;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Document(collection = "alerts")
public class Alert extends AuditableModel {

    // Core details must be final to ensure immutability once the alert is created.
    private final String message;
    private final Long orderId;
    private final LocalDate date;

    // The order situation (e.g., APPROVED, DECLINED) that triggered this specific alert event.
    private final OrderToSupplierSituation situationAtAlert;

    // Required protected default constructor.
    protected Alert() {
        this.message = null;
        this.orderId = null;
        this.date = null;
        this.situationAtAlert = null;
    }

    /**
     * Creates an immutable alert record based on a key order situation change.
     * @param message User notification message.
     * @param orderId ID of the related order.
     * @param situation The specific situation (event trigger).
     */
    public Alert(String message, Long orderId, OrderToSupplierSituation situation) {
        this.message = message;
        this.orderId = orderId;
        this.situationAtAlert = situation;
        this.date = LocalDate.now();
    }

    // Transformations

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
        return this.situationAtAlert.name().replace("_", " ");
    }
}