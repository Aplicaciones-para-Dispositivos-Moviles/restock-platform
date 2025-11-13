package com.restock.platform.subscriptions.domain.model.commands;

/**
 * Update subscription plan command
 * <p>
 *     This class represents the command to update a user's subscription plan.
 * </p>
 * @param userId the user id
 * @param plan the subscription plan value (0: no subscription, 1: monthly plan, 2: annual plan)
 */
public record UpdateSubscriptionPlanCommand(Long userId, int plan) {
}

