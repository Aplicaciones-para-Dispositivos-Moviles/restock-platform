package com.restock.platform.profile.application.internal.queryservices;

import com.restock.platform.profile.domain.model.entities.BusinessCategory;
import com.restock.platform.profile.domain.model.queries.GetAllBusinessCategoriesQuery;
import com.restock.platform.profile.domain.services.BusinessCategoryQueryService;
import com.restock.platform.profile.infrastructure.persistence.mongodb.repositories.BusinessCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessCategoryQueryServiceImpl implements BusinessCategoryQueryService {

    private final BusinessCategoryRepository businessCategoryRepository;

    public BusinessCategoryQueryServiceImpl(BusinessCategoryRepository businessCategoryRepository) {
        this.businessCategoryRepository = businessCategoryRepository;
    }

    @Override
    public List<BusinessCategory> handle(GetAllBusinessCategoriesQuery query) {
        return businessCategoryRepository.findAll();
    }
}
