package com.restock.platform.iam.domain.services;

import com.restock.platform.iam.domain.model.aggregates.User;
import com.restock.platform.iam.domain.model.commands.SignInCommand;
import com.restock.platform.iam.domain.model.commands.SignUpCommand;
import com.restock.platform.iam.domain.model.commands.UpdateUserSubscriptionCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

/**
 * User command service
 * <p>
 *     This interface represents the service to handle user commands.
 * </p>
 */
public interface UserCommandService {
    /**
     * Handle sign in command
     * @param command the {@link SignInCommand} command
     * @return an {@link Optional} of {@link ImmutablePair} of {@link User} and {@link String}
     */
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);

    /**
     * Handle sign up command
     * @param command the {@link SignUpCommand} command
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(SignUpCommand command);

    /**
     * Handle update user subscription command
     * @param command the {@link UpdateUserSubscriptionCommand} command
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(UpdateUserSubscriptionCommand command);

}