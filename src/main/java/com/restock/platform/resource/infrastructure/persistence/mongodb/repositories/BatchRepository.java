package com.restock.platform.resource.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends MongoRepository<Batch, Long> {
    List<Batch> findAllByCustomSupplyId(Long supplyId);
    List<Batch> findAllByUserId(Long userId);
}
