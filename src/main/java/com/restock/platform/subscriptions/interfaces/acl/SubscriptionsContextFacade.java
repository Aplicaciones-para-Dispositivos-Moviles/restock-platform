package com.restock.platform.subscriptions.interfaces.acl;

import com.restock.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.restock.platform.subscriptions.domain.model.commands.UpdateSubscriptionPlanCommand;
import com.restock.platform.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import com.restock.platform.subscriptions.domain.services.SubscriptionCommandService;
import com.restock.platform.subscriptions.domain.services.SubscriptionQueryService;
import org.springframework.stereotype.Service;

/**
 * Subscriptions Context Facade
 * <p>
 * This class is a facade that provides access to the Subscriptions bounded context.
 * It is used to communicate with other bounded contexts through an Anti-Corruption Layer (ACL).
 * </p>
 */
@Service
public class SubscriptionsContextFacade {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsContextFacade(SubscriptionCommandService subscriptionCommandService,
                                     SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Create a subscription for a user
     * @param userId the user id
     * @return the subscription id
     */
    public Long createSubscriptionForUser(Long userId) {
        var command = new CreateSubscriptionCommand(userId);
        var subscription = subscriptionCommandService.handle(command);
        return subscription.map(sub -> sub.getId()).orElse(0L);
    }

    /**
     * Update subscription plan for a user
     * @param userId the user id
     * @param plan the subscription plan value
     */
    public void updateSubscriptionPlanForUser(Long userId, int plan) {
        var command = new UpdateSubscriptionPlanCommand(userId, plan);
        subscriptionCommandService.handle(command);
    }

    /**
     * Check if subscription exists for user
     * @param userId the user id
     * @return true if subscription exists, false otherwise
     */
    public boolean subscriptionExistsForUser(Long userId) {
        var query = new GetSubscriptionByUserIdQuery(userId);
        var subscription = subscriptionQueryService.handle(query);
        return subscription.isPresent();
    }
}

