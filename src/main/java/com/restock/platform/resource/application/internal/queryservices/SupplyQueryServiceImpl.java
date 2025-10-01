package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.entities.Supply;
import com.restock.platform.resource.domain.model.queries.GetAllSuppliesQuery;
import com.restock.platform.resource.domain.model.queries.GetAllSupplyCategoriesQuery;
import com.restock.platform.resource.domain.model.queries.GetSupplyByIdQuery;
import com.restock.platform.resource.domain.services.SupplyQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyQueryServiceImpl implements SupplyQueryService {
    private final SupplyRepository supplyRepository;
    public SupplyQueryServiceImpl(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }
    @Override
    public List<Supply> handle(GetAllSuppliesQuery query) {
        return supplyRepository.findAll();
    }

    @Override
    public Optional<Supply> handle(GetSupplyByIdQuery query){return supplyRepository.findById(query.supplyId());};

    @Override
    public List<String> handle(GetAllSupplyCategoriesQuery query) {
        return supplyRepository.findAll().stream()
                .map(Supply::getCategory)
                .filter(category -> category != null && !category.isBlank())
                .distinct()
                .sorted()
                .toList();
    }
}
