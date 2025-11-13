package com.restock.platform.subscriptions.domain.services;

import com.restock.platform.subscriptions.domain.model.aggregates.Subscription;
import com.restock.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.restock.platform.subscriptions.domain.model.commands.UpdateSubscriptionPlanCommand;

import java.util.Optional;

/**
 * Subscription command service
 * <p>
 *     This interface represents the service to handle subscription commands.
 * </p>
 */
public interface SubscriptionCommandService {

    /**
     * Handle create subscription command
     * @param command the {@link CreateSubscriptionCommand} command
     * @return an {@link Optional} of {@link Subscription} entity
     */
    Optional<Subscription> handle(CreateSubscriptionCommand command);

    /**
     * Handle update subscription plan command
     * @param command the {@link UpdateSubscriptionPlanCommand} command
     * @return an {@link Optional} of {@link Subscription} entity
     */
    Optional<Subscription> handle(UpdateSubscriptionPlanCommand command);
}



