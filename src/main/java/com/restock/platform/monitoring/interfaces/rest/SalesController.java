package com.restock.platform.monitoring.interfaces.rest;


import com.restock.platform.monitoring.application.internal.commandservices.SaleCommandServiceImpl;
import com.restock.platform.monitoring.application.internal.queryservices.SaleQueryServiceImpl;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdQuery;
import com.restock.platform.monitoring.interfaces.rest.resources.AddSaleItemToSaleResource;
import com.restock.platform.monitoring.interfaces.rest.resources.CreateSaleResource;
import com.restock.platform.monitoring.interfaces.rest.resources.SaleResource;
import com.restock.platform.monitoring.interfaces.rest.transform.AddSaleItemToSaleCommandFromResourceAssembler;
import com.restock.platform.monitoring.interfaces.rest.transform.CreateSaleCommandFromResourceAssembler;
import com.restock.platform.monitoring.interfaces.rest.transform.SaleResourceFromEntityAssembler;
import com.restock.platform.planning.domain.services.RecipeCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
    private final RecipeCommandService recipeCommandService;

    public SalesController(SaleCommandServiceImpl saleCommandService, SaleQueryServiceImpl saleQueryService, RecipeCommandService recipeCommandService) {
        this.saleCommandService = saleCommandService;
        this.saleQueryService = saleQueryService;
        this.recipeCommandService = recipeCommandService;
    }

    @GetMapping
    @Operation(summary = "Get all sales")
    public ResponseEntity<List<SaleResource>> getAllSales() {
        var sales = saleQueryService.handle(new GetAllSalesQuery());
        var resources = sales.stream()
                .map(SaleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    @Operation(summary = "Create a new sale")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Recipe created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<SaleResource> createSale(@RequestBody CreateSaleResource resource){
        var createSaleCommand = CreateSaleCommandFromResourceAssembler.toCommandFromResource(resource);
        var saleId = saleCommandService.handle(createSaleCommand);
        if (saleId == null || saleId == 0L) return ResponseEntity.badRequest().build();
        var query = new GetSaleByIdQuery(saleId);
        var saleOptional = saleQueryService.handle(query);
        if(saleOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(saleOptional.get());
        return ResponseEntity.ok(saleResource);
    }

    @PostMapping("/{id}/items")
    @Operation(summary = "Add item to sale")
    @ApiResponses( value ={
            @ApiResponse(responseCode = "201", description = "Item added to sale"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Sale not found", content = @Content)
    })
    public ResponseEntity<Void> addItemToSale( @PathVariable Long id, @RequestBody List<AddSaleItemToSaleResource> items){
        items.forEach(resource->{
            var command = AddSaleItemToSaleCommandFromResourceAssembler.toCommandFromResource(id, resource);
            saleCommandService.handle(command);
        });
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
