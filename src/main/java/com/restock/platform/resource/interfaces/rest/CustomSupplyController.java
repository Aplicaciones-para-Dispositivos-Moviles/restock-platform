package com.restock.platform.resource.interfaces.rest;

import com.restock.platform.resource.domain.model.commands.DeleteCustomSupplyCommand;
import com.restock.platform.resource.domain.model.queries.*;
import com.restock.platform.resource.domain.services.CustomSupplyCommandService;
import com.restock.platform.resource.domain.services.CustomSupplyQueryService;
import com.restock.platform.resource.interfaces.rest.resources.CreateCustomSupplyResource;
import com.restock.platform.resource.interfaces.rest.resources.CustomSupplyResource;
import com.restock.platform.resource.interfaces.rest.resources.UpdateCustomSupplyResource;
import com.restock.platform.resource.interfaces.rest.transform.CreateCustomSupplyCommandFromResourceAssembler;
import com.restock.platform.resource.interfaces.rest.transform.CustomSupplyResourceFromEntityAssembler;
import com.restock.platform.resource.interfaces.rest.transform.UpdateCustomSupplyCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/custom-supplies", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Custom Supplies", description = "Endpoints for managing user-created supplies")
public class CustomSupplyController {

    private final CustomSupplyCommandService customSupplyCommandService;
    private final CustomSupplyQueryService customSupplyQueryService;

    public CustomSupplyController(CustomSupplyCommandService customSupplyCommandService,
                                  CustomSupplyQueryService customSupplyQueryService) {
        this.customSupplyCommandService = customSupplyCommandService;
        this.customSupplyQueryService = customSupplyQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new custom supply")
    public ResponseEntity<CustomSupplyResource> createSupply(@RequestBody CreateCustomSupplyResource resource) {
        var command = CreateCustomSupplyCommandFromResourceAssembler.toCommandFromResource(resource);
        var id = customSupplyCommandService.handle(command);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var result = customSupplyQueryService.handle(new GetCustomSupplyByIdQuery(id));
        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var resourceResponse = CustomSupplyResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return new ResponseEntity<>(resourceResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all custom supplies")
    public ResponseEntity<List<CustomSupplyResource>> getAllSupplies() {
        var supplies = customSupplyQueryService.handle(new GetAllCustomSuppliesQuery());
        var resources = supplies.stream()
                .map(CustomSupplyResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get custom supply by ID")
    public ResponseEntity<CustomSupplyResource> getSupplyById(@PathVariable Long id) {
        return customSupplyQueryService.handle(new GetCustomSupplyByIdQuery(id))
                .map(CustomSupplyResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get custom supplies by user ID")
    public ResponseEntity<List<CustomSupplyResource>> getSuppliesByUserId(@PathVariable Long userId) {
        var supplies = customSupplyQueryService.handle(new GetCustomSuppliesByUserIdQuery(userId));
        var resources = supplies.stream()
                .map(CustomSupplyResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update custom supply")
    public ResponseEntity<CustomSupplyResource> updateSupply(@PathVariable Long id, @RequestBody UpdateCustomSupplyResource resource) {
        var command = UpdateCustomSupplyCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updated = customSupplyCommandService.handle(command);
        if (updated.isEmpty()) return ResponseEntity.notFound().build();

        var result = customSupplyQueryService.handle(new GetCustomSupplyByIdQuery(id));
        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var response = CustomSupplyResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete custom supply")
    public ResponseEntity<Void> deleteSupplyById(@PathVariable Long id) {
        try {
            customSupplyCommandService.handle(new DeleteCustomSupplyCommand(id));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
