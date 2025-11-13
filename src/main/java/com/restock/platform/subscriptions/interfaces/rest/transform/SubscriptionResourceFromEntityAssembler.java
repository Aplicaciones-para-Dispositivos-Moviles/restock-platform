package com.restock.platform.subscriptions.interfaces.rest.transform;

import com.restock.platform.subscriptions.domain.model.aggregates.Subscription;
import com.restock.platform.subscriptions.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {
    public static SubscriptionResource toResourceFromEntity(Subscription subscription) {
        return new SubscriptionResource(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getPlan(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getStatus(),
                subscription.getDurationInMonths()
        );
    }
}



