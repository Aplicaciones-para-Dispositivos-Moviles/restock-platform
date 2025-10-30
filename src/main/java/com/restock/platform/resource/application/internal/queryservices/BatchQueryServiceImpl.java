package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import com.restock.platform.resource.domain.model.queries.*;
import com.restock.platform.resource.domain.services.BatchQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.BatchRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.CustomSupplyRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchQueryServiceImpl implements BatchQueryService {

    private final BatchRepository batchRepository;
    private final CustomSupplyRepository customSupplyRepository;
    private final SupplyRepository supplyRepository;

    public BatchQueryServiceImpl(BatchRepository batchRepository,
                                 CustomSupplyRepository customSupplyRepository,
                                 SupplyRepository supplyRepository) {
        this.batchRepository = batchRepository;
        this.customSupplyRepository = customSupplyRepository;
        this.supplyRepository = supplyRepository;
    }

    private CustomSupply loadCustomSupply(Long customSupplyId) {
        return customSupplyRepository.findById(customSupplyId)
                .map(cs -> {
                    cs.setSupply(supplyRepository.findById(cs.getSupplyId()).orElse(null));
                    return cs;
                })
                .orElse(null);
    }

    @Override
    public List<Batch> handle(GetAllBatchesQuery query) {
        var batches = batchRepository.findAll();
        batches.forEach(batch -> batch.setCustomSupply(loadCustomSupply(batch.getCustomSupplyId())));
        return batches;
    }

    @Override
    public Optional<Batch> handle(GetBatchByIdQuery query) {
        return batchRepository.findById(query.batchId())
                .map(batch -> {
                    batch.setCustomSupply(loadCustomSupply(batch.getCustomSupplyId()));
                    return batch;
                });
    }

    @Override
    public List<Batch> handle(GetBatchesBySupplyIdQuery query) {
        var batches = batchRepository.findAllByCustomSupplyId(query.supplyId());
        batches.forEach(batch -> batch.setCustomSupply(loadCustomSupply(batch.getCustomSupplyId())));
        return batches;
    }

    @Override
    public List<Batch> handle(GetBatchesByUserIdQuery query) {
        var batches = batchRepository.findAllByUserId(query.userId());
        batches.forEach(batch -> batch.setCustomSupply(loadCustomSupply(batch.getCustomSupplyId())));
        return batches;
    }
}
