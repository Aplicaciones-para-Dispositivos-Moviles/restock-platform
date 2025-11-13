package com.restock.platform.monitoring.interfaces.rest;

import com.restock.platform.monitoring.application.internal.commandservices.SaleCommandServiceImpl;
import com.restock.platform.monitoring.application.internal.queryservices.SaleQueryServiceImpl;
import com.restock.platform.monitoring.domain.model.commands.DeleteSaleCommand;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesByUserIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdAndUserIdQuery;
import com.restock.platform.monitoring.interfaces.rest.resources.CreateSaleResource;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleResource;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleResponseResource;
import com.restock.platform.monitoring.interfaces.rest.transform.CreateSaleCommandFromResourceAssembler;
import com.restock.platform.monitoring.interfaces.rest.transform.SaleResourceFromEntityAssembler;
import com.restock.platform.shared.infrastructure.security.AuthenticationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/sales", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sales", description = "Endpoints for monitoring sales")
public class SalesController {
    private final SaleCommandServiceImpl saleCommandService;
    private final SaleQueryServiceImpl saleQueryService;
    private final AuthenticationHelper authenticationHelper;

    public SalesController(SaleCommandServiceImpl saleCommandService,
                          SaleQueryServiceImpl saleQueryService,
                          AuthenticationHelper authenticationHelper) {
        this.saleCommandService = saleCommandService;
        this.saleQueryService = saleQueryService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    @Operation(summary = "Get all sales", description = "Retrieve sales for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sales retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<SaleResource>> getAllSales() {
        Long userId = authenticationHelper.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var sales = saleQueryService.handle(new GetAllSalesByUserIdQuery(userId.intValue()));
        var resources = sales.stream()
            .map(SaleResourceFromEntityAssembler::toResourceFromEntity)
            .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID", description = "Retrieve sale if it belongs to authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sale found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    public ResponseEntity<SaleResource> getSaleById(@PathVariable Long id) {
        Long userId = authenticationHelper.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var saleOptional = saleQueryService.handle(new GetSaleByIdAndUserIdQuery(id, userId.intValue()));

        if (saleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(saleOptional.get());
        return ResponseEntity.ok(saleResource);
    }

    @PostMapping
    @Operation(summary = "Register a new sale", description = "Create a new sale for authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sale registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<SaleResponseResource> createSale(@RequestBody CreateSaleResource resource) {
        Long userId = authenticationHelper.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            var createSaleCommand = CreateSaleCommandFromResourceAssembler.toCommandFromResourceWithUserId(
                resource, userId.intValue()
            );
            var saleId = saleCommandService.handle(createSaleCommand);

            if (saleId == null || saleId == 0L) {
                return ResponseEntity.badRequest().build();
            }

            var query = new GetSaleByIdAndUserIdQuery(saleId, userId.intValue());
            var saleOptional = saleQueryService.handle(query);

            if (saleOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(saleOptional.get());
            var response = new SaleResponseResource(true, saleResource, "Sale registered successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel/Delete a sale", description = "Cancel sale if it belongs to authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sale cancelled successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Sale not found"),
        @ApiResponse(responseCode = "400", description = "Cannot cancel sale")
    })
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        Long userId = authenticationHelper.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            var saleOptional = saleQueryService.handle(new GetSaleByIdAndUserIdQuery(id, userId.intValue()));

            if (saleOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var deleteCommand = new DeleteSaleCommand(id);
            saleCommandService.handle(deleteCommand);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
