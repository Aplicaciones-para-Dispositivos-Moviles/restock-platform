package com.restock.platform.shared.infrastructure.security;

import com.restock.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Service to get the current authenticated user information
 */
@Component
public class AuthenticationHelper {

    /**
     * Get the user ID of the currently authenticated user
     * @return the user ID, or null if not authenticated
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getId();
        }

        return null;
    }

    /**
     * Get the username of the currently authenticated user
     * @return the username, or null if not authenticated
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }

        return null;
    }
}

