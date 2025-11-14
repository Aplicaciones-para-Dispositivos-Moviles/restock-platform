package com.restock.platform.resource.application.internal.commandservices;

import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.domain.model.commands.*;
import com.restock.platform.resource.domain.services.BatchCommandService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.BatchRepository;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.CustomSupplyRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BatchCommandServiceImpl implements BatchCommandService {

    private final BatchRepository batchRepository;
    private final CustomSupplyRepository customSupplyRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public BatchCommandServiceImpl(BatchRepository batchRepository,
                                   CustomSupplyRepository customSupplyRepository,
                                   UserRepository userRepository,
                                   SequenceGeneratorService sequenceGeneratorService) {
        this.batchRepository = batchRepository;
        this.customSupplyRepository = customSupplyRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public Long handle(CreateBatchCommand command) {
        verifySupplyExists(command.customSupplyId());
        var supply = customSupplyRepository.findById(command.customSupplyId())
                .orElseThrow(() -> new IllegalArgumentException("Supply not found with id: " + command.customSupplyId()));

        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + command.userId()));

        var batch = new Batch(command, user.getRole().getId(), supply);
        batch.setId(sequenceGeneratorService.generateSequence("batches_sequence"));
        try {
            batchRepository.save(batch);
        } catch (Exception e) {
            throw new RuntimeException("Error saving batch: " + e.getMessage(), e);
        }
        return batch.getId();
    }

    @Override
    public void handle(DeleteBatchCommand command) {
        verifyBatchExists(command.batchId());
        try {
            batchRepository.deleteById(command.batchId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting batch: " + e.getMessage(), e);
        }
    }
    @Override
    public Optional<Batch> handle(UpdateBatchCommand command) {
        var batch = batchRepository.findById(command.batchId())
                .orElseThrow(() -> new IllegalArgumentException("Batch not found with id: " + command.batchId()));

        try {
            batch.update(command.stock(), command.expirationDate());
            batchRepository.save(batch);
            return Optional.of(batch);
        } catch (Exception e) {
            throw new RuntimeException("Error updating batch: " + e.getMessage(), e);
        }
    }


    private void verifyBatchExists(Long id) {
        if (!batchRepository.existsById(id)) {
            throw new IllegalArgumentException("Batch not found with id: " + id);
        }
    }

    private void verifySupplyExists(Long id) {
        if (!customSupplyRepository.existsById(id)) {
            throw new IllegalArgumentException("Supply not found with id: " + id);
        }
    }
}
