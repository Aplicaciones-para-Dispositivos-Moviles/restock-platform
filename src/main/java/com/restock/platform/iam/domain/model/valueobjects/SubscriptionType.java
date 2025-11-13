package com.restock.platform.iam.domain.model.valueobjects;

/**
 * SubscriptionType value object
 * <p>
 *     This enum represents the subscription types available in the system.
 *     - NO_SUBSCRIPTION (0): No subscription (default value)
 *     - MONTHLY_PLAN (1): Monthly subscription plan
 *     - ANNUAL_PLAN (2): Annual subscription plan
 * </p>
 */
public enum SubscriptionType {
    NO_SUBSCRIPTION(0),
    MONTHLY_PLAN(1),
    ANNUAL_PLAN(2);

    private final int value;

    SubscriptionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Get the subscription type from an integer value
     * @param value the integer value
     * @return the subscription type
     * @throws IllegalArgumentException if the value is not valid
     */
    public static SubscriptionType fromValue(int value) {
        for (SubscriptionType type : SubscriptionType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid subscription type value: " + value);
    }
}

