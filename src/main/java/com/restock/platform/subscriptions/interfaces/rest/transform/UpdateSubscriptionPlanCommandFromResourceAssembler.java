package com.restock.platform.subscriptions.interfaces.rest.transform;

import com.restock.platform.subscriptions.domain.model.commands.UpdateSubscriptionPlanCommand;
import com.restock.platform.subscriptions.interfaces.rest.resources.UpdateSubscriptionPlanResource;

public class UpdateSubscriptionPlanCommandFromResourceAssembler {
    public static UpdateSubscriptionPlanCommand toCommandFromResource(Long userId, UpdateSubscriptionPlanResource resource) {
        return new UpdateSubscriptionPlanCommand(userId, resource.plan());
    }
}

