package com.restock.platform.subscriptions.domain.model.commands;

/**
 * Create subscription command
 * <p>
 *     This class represents the command to create a subscription for a user.
 * </p>
 * @param userId the user id
 */
public record CreateSubscriptionCommand(Long userId) {
}



