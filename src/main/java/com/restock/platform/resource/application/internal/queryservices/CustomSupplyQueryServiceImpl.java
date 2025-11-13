package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import com.restock.platform.resource.domain.model.queries.*;
import com.restock.platform.resource.domain.services.CustomSupplyQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.CustomSupplyRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomSupplyQueryServiceImpl implements CustomSupplyQueryService {

    private final CustomSupplyRepository customSupplyRepository;
    private final SupplyRepository supplyRepository;

    public CustomSupplyQueryServiceImpl(CustomSupplyRepository customSupplyRepository, SupplyRepository supplyRepository) {
        this.customSupplyRepository = customSupplyRepository;
        this.supplyRepository = supplyRepository;

    }

    @Override
    public List<CustomSupply> handle(GetAllCustomSuppliesQuery query) {
        var customSupplies = customSupplyRepository.findAll();
        customSupplies.forEach(cs ->
                cs.setSupply(supplyRepository.findById(cs.getSupplyId()).orElse(null))
        );
        return customSupplies;
    }

    @Override
    public Optional<CustomSupply> handle(GetCustomSupplyByIdQuery query) {
        var customSupply = customSupplyRepository.findById(query.customSupplyId());
        customSupply.ifPresent(cs ->
                cs.setSupply(supplyRepository.findById(cs.getSupplyId()).orElse(null))
        );
        return customSupply;
    }

    @Override
    public List<CustomSupply> handle(GetCustomSuppliesByUserIdQuery query) {
        var customSupplies = customSupplyRepository.findAllByUserId(query.userId());
        customSupplies.forEach(cs ->
                cs.setSupply(supplyRepository.findById(cs.getSupplyId()).orElse(null))
        );
        return customSupplies;
    }
}
