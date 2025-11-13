package com.restock.platform.subscriptions.interfaces.rest.transform;

import com.restock.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.restock.platform.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;

public class CreateSubscriptionCommandFromResourceAssembler {
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(resource.userId());
    }
}

