package com.restock.platform.resource.interfaces.rest;

import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.commands.CreateOrderCommand;
import com.restock.platform.resource.domain.model.commands.UpdateOrderStateCommand;
import com.restock.platform.resource.domain.model.queries.GetAllOrdersQuery;
import com.restock.platform.resource.domain.model.queries.GetOrderByIdQuery;
import com.restock.platform.resource.domain.services.OrderCommandService;
import com.restock.platform.resource.domain.services.OrderQueryService;
import com.restock.platform.resource.interfaces.rest.resources.CreateOrderResource;
import com.restock.platform.resource.interfaces.rest.resources.OrderResource;
import com.restock.platform.resource.interfaces.rest.resources.UpdateOrderStateResource;
import com.restock.platform.resource.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import com.restock.platform.resource.interfaces.rest.transform.OrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Endpoints for managing orders and their internal batch items")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public OrderController(OrderCommandService orderCommandService,
                           OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with optional batch items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderResource> createOrder(@RequestBody CreateOrderResource resource) {
        var command = CreateOrderCommandFromResourceAssembler.toCommandFromResource(resource);
        var orderId = orderCommandService.handle(command);
        var result = orderQueryService.handle(new GetOrderByIdQuery(orderId));

        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return new ResponseEntity<>(orderResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve all orders from MongoDB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderResource>> getAllOrders() {
        var orders = orderQueryService.handle(new GetAllOrdersQuery());
        var resources = orders.stream()
                .map(OrderResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID including batch items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResource> getOrderById(@PathVariable Long id) {
        return orderQueryService.handle(new GetOrderByIdQuery(id))
                .map(OrderResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/state")
    @Operation(summary = "Update order state/situation", description = "Update the state and situation of an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResource> updateOrderState(
            @PathVariable Long id,
            @RequestBody UpdateOrderStateResource resource) {

        var command = new UpdateOrderStateCommand(id, resource.newState(), resource.newSituation());
        var result = orderCommandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var updatedResource = OrderResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderCommandService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
