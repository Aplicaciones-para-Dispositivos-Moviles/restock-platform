package com.restock.platform.iam.application.internal.commandservices;

import com.restock.platform.iam.domain.model.commands.SeedRolesCommand;
import com.restock.platform.iam.domain.model.entities.Role;
import com.restock.platform.iam.domain.model.valueobjects.Roles;
import com.restock.platform.iam.domain.services.RoleCommandService;
import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.RoleRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation of {@link RoleCommandService} to handle {@link SeedRolesCommand}
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public RoleCommandServiceImpl(RoleRepository roleRepository,
                                  SequenceGeneratorService sequenceGeneratorService) {
        this.roleRepository = roleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    /**
     * This method will handle the {@link SeedRolesCommand} and will create the roles if not exists
     * @param command {@link SeedRolesCommand}
     * @see SeedRolesCommand
     */
    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if(!roleRepository.existsByName(role)) {
                var roleEntity = new Role(Roles.valueOf(role.name()));
                roleEntity.setId(sequenceGeneratorService.generateSequence("roles_sequence"));
                roleRepository.save(roleEntity);
            }
        } );
    }
}