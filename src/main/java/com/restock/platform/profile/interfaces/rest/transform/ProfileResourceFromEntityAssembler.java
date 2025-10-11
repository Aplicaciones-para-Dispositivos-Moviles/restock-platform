package com.restock.platform.profile.interfaces.rest.transform;

import com.restock.platform.profile.domain.model.entities.Profile;
import com.restock.platform.profile.interfaces.rest.resources.BusinessCategoryResource;
import com.restock.platform.profile.interfaces.rest.resources.ProfileResource;

import java.util.List;

public class ProfileResourceFromEntityAssembler {

    public static ProfileResource toResourceFromEntity(Long userId, Profile profile) {
        List<BusinessCategoryResource> businessCategories = profile.getBusinessCategories() == null ? List.of() : profile.getBusinessCategories().stream()
                .map(category -> new BusinessCategoryResource(category.getId(), category.getName()))
                .toList();

        return new ProfileResource(
                userId,
                profile.getFirstName(),
                profile.getLastName(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getCountry(),
                profile.getAvatar(),
                profile.getBusinessName(),
                profile.getBusinessAddress(),
                profile.getDescription(),
                businessCategories
        );
    }
}
