package com.restock.platform.resource.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.resource.domain.model.aggregates.CustomSupply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomSupplyRepository extends MongoRepository<CustomSupply, Long> {
    List<CustomSupply> findAllByUserId(Long userId);
}
