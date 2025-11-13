package com.restock.platform.iam.interfaces.rest.transform;

import com.restock.platform.iam.domain.model.commands.UpdateUserSubscriptionCommand;
import com.restock.platform.iam.interfaces.rest.resources.UpdateSubscriptionResource;

public class UpdateUserSubscriptionCommandFromResourceAssembler {
    public static UpdateUserSubscriptionCommand toCommandFromResource(Long userId, UpdateSubscriptionResource resource) {
        return new UpdateUserSubscriptionCommand(userId, resource.subscription());
    }
}

