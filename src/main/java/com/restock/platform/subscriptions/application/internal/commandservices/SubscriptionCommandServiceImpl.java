package com.restock.platform.subscriptions.application.internal.commandservices;

import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import com.restock.platform.subscriptions.domain.model.aggregates.Subscription;
import com.restock.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.restock.platform.subscriptions.domain.model.commands.UpdateSubscriptionPlanCommand;
import com.restock.platform.subscriptions.domain.services.SubscriptionCommandService;
import com.restock.platform.subscriptions.infrastructure.persistence.mongodb.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Subscription command service implementation
 * <p>
 *     This class implements the {@link SubscriptionCommandService} interface and provides the implementation for the
 *     {@link CreateSubscriptionCommand} and {@link UpdateSubscriptionPlanCommand} commands.
 * </p>
 */
@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository,
                                         SequenceGeneratorService sequenceGeneratorService) {
        this.subscriptionRepository = subscriptionRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    /**
     * Handle the create subscription command
     * <p>
     *     This method handles the {@link CreateSubscriptionCommand} command and returns the subscription.
     * </p>
     * @param command the create subscription command containing the user id
     * @return the created subscription
     */
    @Override
    public Optional<Subscription> handle(CreateSubscriptionCommand command) {
        // Check if subscription already exists for this user
        if (subscriptionRepository.existsByUserId(command.userId())) {
            throw new RuntimeException("Subscription already exists for user with id: " + command.userId());
        }

        var subscription = new Subscription(command.userId());
        subscription.setId(sequenceGeneratorService.generateSequence("subscriptions_sequence"));
        subscriptionRepository.save(subscription);

        return subscriptionRepository.findByUserId(command.userId());
    }

    /**
     * Handle the update subscription plan command
     * <p>
     *     This method handles the {@link UpdateSubscriptionPlanCommand} command and returns the updated subscription.
     * </p>
     * @param command the update subscription plan command containing the user id and plan
     * @return the updated subscription
     */
    @Override
    public Optional<Subscription> handle(UpdateSubscriptionPlanCommand command) {
        var subscription = subscriptionRepository.findByUserId(command.userId())
                .orElseThrow(() -> new RuntimeException("Subscription not found for user with id: " + command.userId()));

        subscription.updatePlan(command.plan());
        subscription.updateStatus();
        subscriptionRepository.save(subscription);

        return Optional.of(subscription);
    }
}



