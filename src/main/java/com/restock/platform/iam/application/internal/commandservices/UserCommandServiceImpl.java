package com.restock.platform.iam.application.internal.commandservices;

import com.restock.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.restock.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.restock.platform.iam.domain.model.aggregates.User;
import com.restock.platform.iam.domain.model.commands.SignInCommand;
import com.restock.platform.iam.domain.model.commands.SignUpCommand;
import com.restock.platform.iam.domain.model.commands.UpdateUserSubscriptionCommand;
import com.restock.platform.iam.domain.services.UserCommandService;
import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.RoleRepository;
import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.restock.platform.shared.domain.exceptions.InvalidCredentialsException;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import com.restock.platform.subscriptions.interfaces.acl.SubscriptionsContextFacade;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final SubscriptionsContextFacade subscriptionsContextFacade;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService,
                                  RoleRepository roleRepository, SequenceGeneratorService sequenceGeneratorService,
                                  SubscriptionsContextFacade subscriptionsContextFacade) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.subscriptionsContextFacade = subscriptionsContextFacade;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new InvalidCredentialsException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new InvalidCredentialsException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        var role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        var user = new User(command.username(), hashingService.encode(command.password()), role);
        user.setId(sequenceGeneratorService.generateSequence("users_sequence"));
        userRepository.save(user);

        // Create subscription for the new user
        try {
            subscriptionsContextFacade.createSubscriptionForUser(user.getId());
        } catch (Exception e) {
            // Log the error but don't fail user creation
            System.err.println("Failed to create subscription for user " + user.getId() + ": " + e.getMessage());
        }

        return userRepository.findByUsername(command.username());
    }

    /**
     * Handle the update user subscription command
     * <p>
     *     This method handles the {@link UpdateUserSubscriptionCommand} command and returns the updated user.
     * </p>
     * @param command the update user subscription command containing the user id and subscription value
     * @return the updated user
     * @throws RuntimeException if the user is not found
     */
    @Override
    public Optional<User> handle(UpdateUserSubscriptionCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateSubscription(command.subscription());
        userRepository.save(user);

        // Update subscription in Subscriptions bounded context
        try {
            // Check if subscription exists, if not create it
            if (!subscriptionsContextFacade.subscriptionExistsForUser(user.getId())) {
                subscriptionsContextFacade.createSubscriptionForUser(user.getId());
            }
            // Update the subscription plan
            subscriptionsContextFacade.updateSubscriptionPlanForUser(user.getId(), command.subscription());
        } catch (Exception e) {
            // Log the error but don't fail user update
            System.err.println("Failed to update subscription for user " + user.getId() + ": " + e.getMessage());
        }

        return Optional.of(user);
    }
}