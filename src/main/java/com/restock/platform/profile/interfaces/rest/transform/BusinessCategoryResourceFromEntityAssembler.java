package com.restock.platform.profile.interfaces.rest.transform;

import com.restock.platform.profile.domain.model.entities.BusinessCategory;
import com.restock.platform.profile.interfaces.rest.resources.BusinessCategoryResource;

public class BusinessCategoryResourceFromEntityAssembler {

    public static BusinessCategoryResource toResourceFromEntity(BusinessCategory category) {
        return new BusinessCategoryResource(category.getId(), category.getName());
    }
}
