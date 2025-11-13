package com.restock.platform.subscriptions.application.internal.queryservices;

import com.restock.platform.subscriptions.domain.model.aggregates.Subscription;
import com.restock.platform.subscriptions.domain.model.queries.GetAllSubscriptionsQuery;
import com.restock.platform.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import com.restock.platform.subscriptions.domain.services.SubscriptionQueryService;
import com.restock.platform.subscriptions.infrastructure.persistence.mongodb.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Subscription query service implementation
 * <p>
 *     This class implements the {@link SubscriptionQueryService} interface and provides the implementation for the
 *     {@link GetSubscriptionByUserIdQuery} and {@link GetAllSubscriptionsQuery} queries.
 * </p>
 */
@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Handle the get subscription by user id query
     * <p>
     *     This method handles the {@link GetSubscriptionByUserIdQuery} query and returns the subscription.
     * </p>
     * @param query the get subscription by user id query
     * @return an optional containing the subscription if found
     */
    @Override
    public Optional<Subscription> handle(GetSubscriptionByUserIdQuery query) {
        var subscription = subscriptionRepository.findByUserId(query.userId());
        // Update status before returning
        subscription.ifPresent(sub -> {
            sub.updateStatus();
            subscriptionRepository.save(sub);
        });
        return subscription;
    }

    /**
     * Handle the get all subscriptions query
     * <p>
     *     This method handles the {@link GetAllSubscriptionsQuery} query and returns all subscriptions.
     * </p>
     * @param query the get all subscriptions query
     * @return a list of all subscriptions
     */
    @Override
    public List<Subscription> handle(GetAllSubscriptionsQuery query) {
        var subscriptions = subscriptionRepository.findAll();
        // Update status for all subscriptions
        subscriptions.forEach(sub -> {
            sub.updateStatus();
            subscriptionRepository.save(sub);
        });
        return subscriptions;
    }
}

