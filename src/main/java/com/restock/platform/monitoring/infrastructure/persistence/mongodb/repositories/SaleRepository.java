package com.restock.platform.monitoring.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleCode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SaleRepository extends MongoRepository<Sale, Long> {
    List<Sale> findBySaleCode(SaleCode saleCode);
}
