package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.domain.model.queries.*;
import com.restock.platform.resource.domain.services.BatchQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchQueryServiceImpl implements BatchQueryService {

    private final BatchRepository batchRepository;

    public BatchQueryServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public List<Batch> handle(GetAllBatchesQuery query) {
        return batchRepository.findAll();
    }

    @Override
    public Optional<Batch> handle(GetBatchByIdQuery query) {
        return batchRepository.findById(query.batchId());
    }

    @Override
    public List<Batch> handle(GetBatchesBySupplyIdQuery query) {
        return batchRepository.findAllByCustomSupplyId(query.supplyId());
    }

    @Override
    public List<Batch> handle(GetBatchesByUserIdQuery query) {
        return batchRepository.findAllByUserId(query.userId());
    }
}
