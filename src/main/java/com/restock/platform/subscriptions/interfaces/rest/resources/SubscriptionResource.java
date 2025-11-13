package com.restock.platform.subscriptions.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Subscription resource
 * <p>
 *     This class represents the subscription resource exposed by the REST API.
 * </p>
 * @param id the subscription id
 * @param userId the user id
 * @param plan the subscription plan (0: none, 1: monthly, 2: annual)
 * @param startDate the start date of the subscription
 * @param endDate the end date of the subscription
 * @param status the status of the subscription (none, active, expired)
 * @param durationInMonths the duration of the subscription in months
 */
public record SubscriptionResource(
        Long id,
        Long userId,
        int plan,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String status,
        int durationInMonths
) {
}



