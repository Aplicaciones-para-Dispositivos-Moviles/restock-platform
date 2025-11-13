package com.restock.platform.subscriptions.domain.services;

import com.restock.platform.subscriptions.domain.model.aggregates.Subscription;
import com.restock.platform.subscriptions.domain.model.queries.GetAllSubscriptionsQuery;
import com.restock.platform.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Subscription query service
 * <p>
 *     This interface represents the service to handle subscription queries.
 * </p>
 */
public interface SubscriptionQueryService {

    /**
     * Handle get subscription by user id query
     * @param query the {@link GetSubscriptionByUserIdQuery} query
     * @return an {@link Optional} of {@link Subscription} entity
     */
    Optional<Subscription> handle(GetSubscriptionByUserIdQuery query);

    /**
     * Handle get all subscriptions query
     * @param query the {@link GetAllSubscriptionsQuery} query
     * @return a {@link List} of {@link Subscription} entities
     */
    List<Subscription> handle(GetAllSubscriptionsQuery query);
}

