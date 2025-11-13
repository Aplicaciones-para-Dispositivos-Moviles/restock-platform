package com.restock.platform.subscriptions.interfaces.rest;

import com.restock.platform.subscriptions.domain.model.queries.GetAllSubscriptionsQuery;
import com.restock.platform.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import com.restock.platform.subscriptions.domain.services.SubscriptionCommandService;
import com.restock.platform.subscriptions.domain.services.SubscriptionQueryService;
import com.restock.platform.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.restock.platform.subscriptions.interfaces.rest.resources.SubscriptionResource;
import com.restock.platform.subscriptions.interfaces.rest.resources.UpdateSubscriptionPlanResource;
import com.restock.platform.subscriptions.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.restock.platform.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.restock.platform.subscriptions.interfaces.rest.transform.UpdateSubscriptionPlanCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Subscriptions REST Controller
 * <p>
 * This class is a REST controller that exposes the subscriptions resource.
 * It includes the following operations:
 * - GET /api/v1/subscriptions: returns all subscriptions
 * - GET /api/v1/subscriptions/user/{userId}: returns the subscription for a specific user
 * - POST /api/v1/subscriptions: creates a new subscription
 * - PUT /api/v1/subscriptions/user/{userId}: updates the subscription plan for a specific user
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Available Subscription Endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Get all subscriptions
     * @return a list of all subscriptions
     */
    @GetMapping
    @Operation(summary = "Get all subscriptions", description = "Get all subscriptions in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscriptions retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<List<SubscriptionResource>> getAllSubscriptions() {
        var getAllSubscriptionsQuery = new GetAllSubscriptionsQuery();
        var subscriptions = subscriptionQueryService.handle(getAllSubscriptionsQuery);
        var subscriptionResources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(subscriptionResources);
    }

    /**
     * Get subscription by user id
     * <p>
     * This endpoint returns the subscription information for a specific user including:
     * - Subscription plan type (0: none, 1: monthly, 2: annual)
     * - Start date of the subscription
     * - End date of the subscription
     * - Subscription status (none, active, expired)
     * - Duration in months
     * </p>
     * @param userId the user id
     * @return the subscription resource
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get subscription by user ID",
               description = "Get the subscription information for a specific user. Returns plan type, dates, status, and duration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Subscription not found."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<SubscriptionResource> getSubscriptionByUserId(@PathVariable Long userId) {
        var getSubscriptionByUserIdQuery = new GetSubscriptionByUserIdQuery(userId);
        var subscription = subscriptionQueryService.handle(getSubscriptionByUserIdQuery);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return ResponseEntity.ok(subscriptionResource);
    }

    /**
     * Create a new subscription
     * @param resource the create subscription resource
     * @return the created subscription resource
     */
    @PostMapping
    @Operation(summary = "Create subscription", description = "Create a new subscription for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request or subscription already exists."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<SubscriptionResource> createSubscription(@RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var subscription = subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return new ResponseEntity<>(subscriptionResource, HttpStatus.CREATED);
    }

    /**
     * Update subscription plan for a user
     * <p>
     * This endpoint updates the subscription plan for a specific user.
     * When the plan is updated, it automatically:
     * - Sets the start date to now
     * - Calculates the end date based on the plan (1 month or 12 months)
     * - Updates the status to active
     * - Sets the duration in months
     * </p>
     * @param userId the user id
     * @param resource the update subscription plan resource
     * @return the updated subscription resource
     */
    @PutMapping("/user/{userId}")
    @Operation(summary = "Update subscription plan",
               description = "Update the subscription plan for a specific user. Automatically updates dates, status, and duration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription plan updated successfully."),
            @ApiResponse(responseCode = "404", description = "Subscription not found."),
            @ApiResponse(responseCode = "400", description = "Invalid plan value."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<SubscriptionResource> updateSubscriptionPlan(
            @PathVariable Long userId,
            @RequestBody UpdateSubscriptionPlanResource resource) {

        var command = UpdateSubscriptionPlanCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var subscription = subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return ResponseEntity.ok(subscriptionResource);
    }
}

