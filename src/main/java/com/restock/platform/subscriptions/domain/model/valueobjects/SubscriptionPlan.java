package com.restock.platform.subscriptions.domain.model.valueobjects;

/**
 * SubscriptionPlan value object
 * <p>
 *     This enum represents the subscription plans available in the system.
 *     - NO_SUBSCRIPTION (0): No subscription (default value)
 *     - MONTHLY_PLAN (1): Monthly subscription plan
 *     - ANNUAL_PLAN (2): Annual subscription plan
 * </p>
 */
public enum SubscriptionPlan {
    NO_SUBSCRIPTION(0),
    MONTHLY_PLAN(1),
    ANNUAL_PLAN(2);

    private final int value;

    SubscriptionPlan(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Get the subscription plan from an integer value
     * @param value the integer value
     * @return the subscription plan
     * @throws IllegalArgumentException if the value is not valid
     */
    public static SubscriptionPlan fromValue(int value) {
        for (SubscriptionPlan plan : SubscriptionPlan.values()) {
            if (plan.value == value) {
                return plan;
            }
        }
        throw new IllegalArgumentException("Invalid subscription plan value: " + value);
    }
}



