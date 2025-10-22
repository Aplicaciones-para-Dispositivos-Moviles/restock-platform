package com.restock.platform.resource.application.internal.commandservices;

import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import com.restock.platform.resource.domain.model.commands.CreateCustomSupplyCommand;
import com.restock.platform.resource.domain.model.commands.DeleteCustomSupplyCommand;
import com.restock.platform.resource.domain.model.commands.UpdateCustomSupplyCommand;
import com.restock.platform.resource.domain.services.CustomSupplyCommandService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.CustomSupplyRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomSupplyCommandServiceImpl implements CustomSupplyCommandService {

    private final CustomSupplyRepository customSupplyRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public CustomSupplyCommandServiceImpl(CustomSupplyRepository customSupplyRepository,
                                          SequenceGeneratorService sequenceGeneratorService) {
        this.customSupplyRepository = customSupplyRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public Long handle(CreateCustomSupplyCommand command) {
        var supply = new CustomSupply(command);
        supply.setId(sequenceGeneratorService.generateSequence("custom_supplies_sequence"));
        try {
            customSupplyRepository.save(supply);
        } catch (Exception e) {
            throw new RuntimeException("Error saving supply: " + e.getMessage(), e);
        }

        return supply.getId();
    }

    @Override
    public Optional<CustomSupply> handle(UpdateCustomSupplyCommand command) {
        var supply = customSupplyRepository.findById(command.supplyId())
                .orElseThrow(() -> new IllegalArgumentException("Supply not found with id: " + command.supplyId()));

        try {
            var updatedSupply = supply.update(command.stockRange(), command.price(), command.description(), command.unitMeasurement());
            customSupplyRepository.save(updatedSupply);
            return Optional.of(updatedSupply);
        } catch (Exception e) {
            throw new RuntimeException("Error updating supply: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteCustomSupplyCommand command) {
        if (!customSupplyRepository.existsById(command.supplyId())) {
            throw new IllegalArgumentException("Supply not found with id: " + command.supplyId());
        }
        try {
            customSupplyRepository.deleteById(command.supplyId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting supply: " + e.getMessage(), e);
        }
    }


}
