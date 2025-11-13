package com.restock.platform.iam.domain.model.commands;

/**
 * Update user subscription command
 * <p>
 *     This class represents the command to update a user's subscription.
 * </p>
 * @param userId the user id
 * @param subscription the subscription value (0: no subscription, 1: monthly plan, 2: annual plan)
 */
public record UpdateUserSubscriptionCommand(Long userId, int subscription) {
}

