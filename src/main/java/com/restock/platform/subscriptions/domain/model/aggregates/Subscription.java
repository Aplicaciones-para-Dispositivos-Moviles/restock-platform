package com.restock.platform.subscriptions.domain.model.aggregates;

import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.restock.platform.subscriptions.domain.model.valueobjects.SubscriptionPlan;
import com.restock.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Subscription aggregate root
 * This class represents the aggregate root for the Subscription entity.
 * It contains all the information about a user's subscription including plan type,
 * start date, duration, and status.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Document(collection = "subscriptions")
@Getter
@Setter
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {

    @Indexed(unique = true)
    private Long userId;

    private int plan; // 0: none, 1: monthly, 2: annual

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status; // "active", "expired", "none"

    private int durationInMonths; // 1 for monthly, 12 for annual, 0 for none

    public Subscription() {
    }

    public Subscription(Long userId) {
        this.userId = userId;
        this.plan = SubscriptionPlan.NO_SUBSCRIPTION.getValue();
        this.status = SubscriptionStatus.NONE.name().toLowerCase();
        this.durationInMonths = 0;
    }

    /**
     * Update subscription plan
     * @param planValue the plan value (0, 1, or 2)
     */
    public void updatePlan(int planValue) {
        SubscriptionPlan subscriptionPlan = SubscriptionPlan.fromValue(planValue);
        this.plan = planValue;

        if (planValue == 0) {
            // No subscription
            this.startDate = null;
            this.endDate = null;
            this.status = SubscriptionStatus.NONE.name().toLowerCase();
            this.durationInMonths = 0;
        } else if (planValue == 1) {
            // Monthly plan
            this.startDate = LocalDateTime.now();
            this.endDate = this.startDate.plusMonths(1);
            this.status = SubscriptionStatus.ACTIVE.name().toLowerCase();
            this.durationInMonths = 1;
        } else if (planValue == 2) {
            // Annual plan
            this.startDate = LocalDateTime.now();
            this.endDate = this.startDate.plusYears(1);
            this.status = SubscriptionStatus.ACTIVE.name().toLowerCase();
            this.durationInMonths = 12;
        }
    }

    /**
     * Update subscription status based on current date
     */
    public void updateStatus() {
        if (this.plan == 0) {
            this.status = SubscriptionStatus.NONE.name().toLowerCase();
        } else if (this.endDate != null && LocalDateTime.now().isAfter(this.endDate)) {
            this.status = SubscriptionStatus.EXPIRED.name().toLowerCase();
        } else if (this.startDate != null && this.endDate != null) {
            this.status = SubscriptionStatus.ACTIVE.name().toLowerCase();
        }
    }

    /**
     * Get subscription plan enum
     * @return the subscription plan
     */
    public SubscriptionPlan getSubscriptionPlan() {
        return SubscriptionPlan.fromValue(this.plan);
    }

    /**
     * Get subscription status enum
     * @return the subscription status
     */
    public SubscriptionStatus getSubscriptionStatus() {
        return SubscriptionStatus.valueOf(this.status.toUpperCase());
    }
}

