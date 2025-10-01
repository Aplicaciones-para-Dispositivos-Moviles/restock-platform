package com.restock.platform.profile.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.profile.domain.model.entities.BusinessCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCategoryRepository extends MongoRepository<BusinessCategory, String> {
}
