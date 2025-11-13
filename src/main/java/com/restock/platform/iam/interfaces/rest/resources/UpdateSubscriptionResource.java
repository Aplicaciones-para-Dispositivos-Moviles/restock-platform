package com.restock.platform.iam.interfaces.rest.resources;

/**
 * Update subscription resource
 * <p>
 *     This class represents the resource to update a user's subscription.
 * </p>
 * @param subscription the subscription value (0: no subscription, 1: monthly plan, 2: annual plan)
 */
public record UpdateSubscriptionResource(int subscription) {
}

