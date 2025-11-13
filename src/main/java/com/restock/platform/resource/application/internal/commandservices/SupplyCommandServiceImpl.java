package com.restock.platform.resource.application.internal.commandservices;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restock.platform.resource.domain.model.commands.SeedSuppliesCommand;
import com.restock.platform.resource.domain.model.commands.CreateSupplyCommand;
import com.restock.platform.resource.domain.model.entities.Supply;
import com.restock.platform.resource.domain.services.SupplyCommandService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.SupplyRepository;
import com.restock.platform.resource.interfaces.rest.resources.SupplyResource;
import com.restock.platform.resource.interfaces.rest.transform.CreateSupplyCommandFromResourceAssembler;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class SupplyCommandServiceImpl implements SupplyCommandService {

    private final SupplyRepository supplyRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public SupplyCommandServiceImpl(SupplyRepository supplyRepository,
                                    SequenceGeneratorService sequenceGeneratorService) {
        this.supplyRepository = supplyRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void handle(SeedSuppliesCommand command) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("jsonData/supplies.json").getInputStream();

            List<SupplyResource> supplyResources = mapper.readValue(inputStream, new TypeReference<>() {});

            for (SupplyResource resource : supplyResources) {
                if (!supplyRepository.existsByName(resource.name())) {
                    CreateSupplyCommand cmd = CreateSupplyCommandFromResourceAssembler.toCommandFromResource(resource);

                    Supply supply = new Supply(
                            cmd.name(),
                            cmd.description(),
                            cmd.perishable(),
                            cmd.category()
                    );

                    supply.setId(sequenceGeneratorService.generateSequence("supplies_sequence"));
                    supplyRepository.save(supply);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading supplies.json", e);
        }
    }
}
