package com.restock.platform.iam.interfaces.rest.transform;

import com.restock.platform.iam.domain.model.aggregates.User;
import com.restock.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), user.getRole().getId(), user.getSubscription(), token);
    }
}