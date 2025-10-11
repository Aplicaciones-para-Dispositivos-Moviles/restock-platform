package com.restock.platform.resource.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.resource.domain.model.entities.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyRepository extends MongoRepository<Supply, Long> {

    Optional<Supply> findByName(String name);
    boolean existsByName(String name);

}