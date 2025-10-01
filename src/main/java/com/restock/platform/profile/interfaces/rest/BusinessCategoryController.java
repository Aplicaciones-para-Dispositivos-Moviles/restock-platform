package com.restock.platform.profile.interfaces.rest;

import com.restock.platform.profile.domain.model.queries.GetAllBusinessCategoriesQuery;
import com.restock.platform.profile.domain.services.BusinessCategoryQueryService;
import com.restock.platform.profile.interfaces.rest.resources.BusinessCategoryResource;
import com.restock.platform.profile.interfaces.rest.transform.BusinessCategoryResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/business-categories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Business Categories", description = "Endpoints for managing business categories")
public class BusinessCategoryController {

    private final BusinessCategoryQueryService businessCategoryQueryService;

    public BusinessCategoryController(BusinessCategoryQueryService businessCategoryQueryService) {
        this.businessCategoryQueryService = businessCategoryQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all business categories")
    public ResponseEntity<List<BusinessCategoryResource>> getAllBusinessCategories() {
        var categories = businessCategoryQueryService.handle(new GetAllBusinessCategoriesQuery());
        if (categories.isEmpty()) return ResponseEntity.notFound().build();

        var resources = categories.stream()
                .map(BusinessCategoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
