package com.restock.platform.resource.interfaces.rest;

import com.restock.platform.resource.domain.model.queries.GetAllAlertsByAdminRestaurantIdQuery;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsBySupplierIdQuery;
import com.restock.platform.resource.domain.services.AlertQueryService;
import com.restock.platform.resource.interfaces.rest.resources.AlertResource;
import com.restock.platform.resource.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Endpoints for retrieving alerts for suppliers and restaurant administrators")
public class AlertController {

    private final AlertQueryService alertQueryService;
    private final AlertResourceFromEntityAssembler assembler;

    public AlertController(AlertQueryService alertQueryService) {
        this.alertQueryService = alertQueryService;
        // Se inicializa el Assembler (siguiendo el patrón de tu OrderController)
        this.assembler = new AlertResourceFromEntityAssembler();
    }

    /**
     * Get all alerts for a specific Supplier.
     *
     * @param supplierId The ID of the supplier.
     * @return A list of {@link AlertResource} for the supplier.
     */
    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get all alerts for a Supplier", description = "Retrieve all alerts associated with a given supplier ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully")
    })
    public ResponseEntity<List<AlertResource>> getAlertsBySupplierId(@PathVariable Long supplierId) {
        var query = new GetAllAlertsBySupplierIdQuery(supplierId);
        var alerts = alertQueryService.handle(query);
        var resources = alerts.stream()
                .map(assembler::toResource)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Get all alerts for a specific Restaurant Administrator.
     *
     * @param adminRestaurantId The ID of the restaurant administrator.
     * @return A list of {@link AlertResource} for the restaurant administrator.
     */
    @GetMapping("/admin-restaurant/{adminRestaurantId}")
    @Operation(summary = "Get all alerts for a Restaurant Administrator", description = "Retrieve all alerts associated with a given restaurant administrator ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully")
    })
    public ResponseEntity<List<AlertResource>> getAlertsByAdminRestaurantId(@PathVariable Long adminRestaurantId) {
        var query = new GetAllAlertsByAdminRestaurantIdQuery(adminRestaurantId);
        var alerts = alertQueryService.handle(query);
        var resources = alerts.stream()
                .map(assembler::toResource)
                .toList();
        return ResponseEntity.ok(resources);
    }
}