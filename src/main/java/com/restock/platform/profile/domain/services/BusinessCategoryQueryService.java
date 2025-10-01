package com.restock.platform.profile.domain.services;

import com.restock.platform.profile.domain.model.entities.BusinessCategory;
import com.restock.platform.profile.domain.model.queries.GetAllBusinessCategoriesQuery;

import java.util.List;

public interface BusinessCategoryQueryService {
    List<BusinessCategory> handle(GetAllBusinessCategoriesQuery query);
}
