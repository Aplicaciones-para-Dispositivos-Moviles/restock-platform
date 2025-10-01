package com.restock.platform.profile.application.internal.commandservices;

import com.restock.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.restock.platform.profile.domain.model.commands.DeleteProfileCommand;
import com.restock.platform.profile.domain.model.commands.UpdateBusinessInformationCommand;
import com.restock.platform.profile.domain.model.commands.UpdatePasswordCommand;
import com.restock.platform.profile.domain.model.commands.UpdatePersonalInformationCommand;
import com.restock.platform.profile.domain.model.entities.BusinessCategoryItem;
import com.restock.platform.profile.domain.model.entities.Profile;
import com.restock.platform.profile.domain.services.ProfileCommandService;
import com.restock.platform.profile.infrastructure.persistence.mongodb.repositories.BusinessCategoryRepository;
import com.restock.platform.shared.domain.exceptions.InvalidCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final UserRepository userRepository;
    private final BusinessCategoryRepository businessCategoryRepository;
    private final HashingService hashingService;

    public ProfileCommandServiceImpl(UserRepository userRepository,
                                     BusinessCategoryRepository businessCategoryRepository,
                                     HashingService hashingService) {
        this.userRepository = userRepository;
        this.businessCategoryRepository = businessCategoryRepository;
        this.hashingService = hashingService;
    }

    @Override
    public Optional<Profile> handle(UpdatePersonalInformationCommand command) {
        var userOptional = userRepository.findById(command.userId());
        if (userOptional.isEmpty()) return Optional.empty();

        var user = userOptional.get();
        var profile = user.getProfile();
        if (profile == null) profile = new Profile();

        profile.setFirstName(command.firstName());
        profile.setLastName(command.lastName());
        profile.setEmail(command.email());
        profile.setPhone(command.phone());
        profile.setAddress(command.address());
        profile.setCountry(command.country());
        profile.setAvatar(command.avatar());

        user.setProfile(profile);
        userRepository.save(user);
        return Optional.of(profile);
    }

    @Override
    public Optional<Profile> handle(UpdateBusinessInformationCommand command) {
        var userOptional = userRepository.findById(command.userId());
        if (userOptional.isEmpty()) return Optional.empty();

        var user = userOptional.get();
        var profile = user.getProfile();
        if (profile == null) profile = new Profile();

        profile.setBusinessName(command.businessName());
        profile.setBusinessAddress(command.businessAddress());
        profile.setDescription(command.description());

        var requestedCategoryIds = command.businessCategoryIds() == null ? List.<String>of() : command.businessCategoryIds();

        List<BusinessCategoryItem> businessCategories = businessCategoryRepository.findAllById(requestedCategoryIds)
                .stream()
                .map(category -> new BusinessCategoryItem(category.getId(), category.getName()))
                .toList();

        if (businessCategories.size() != requestedCategoryIds.size()) {
            throw new RuntimeException("One or more business categories were not found");
        }

        profile.setBusinessCategories(businessCategories);
        user.setProfile(profile);
        userRepository.save(user);
        return Optional.of(profile);
    }

    @Override
    public boolean handle(UpdatePasswordCommand command) {
        var userOptional = userRepository.findById(command.userId());
        if (userOptional.isEmpty()) return false;

        var user = userOptional.get();
        if (!hashingService.matches(command.currentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid current password");
        }

        user.setPassword(hashingService.encode(command.newPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean handle(DeleteProfileCommand command) {
        var userOptional = userRepository.findById(command.userId());
        if (userOptional.isEmpty()) return false;

        var user = userOptional.get();
        var profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
        } else {
            profile.clear();
        }
        user.setProfile(profile);
        userRepository.save(user);
        return true;
    }
}
