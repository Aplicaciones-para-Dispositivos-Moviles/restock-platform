package com.restock.platform.subscriptions.interfaces.rest.resources;

/**
 * Update subscription plan resource
 * <p>
 *     This class represents the resource to update a subscription plan.
 * </p>
 * @param plan the subscription plan (0: none, 1: monthly, 2: annual)
 */
public record UpdateSubscriptionPlanResource(int plan) {
}

