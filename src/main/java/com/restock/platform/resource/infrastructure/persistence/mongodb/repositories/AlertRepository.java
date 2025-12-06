package com.restock.platform.resource.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.resource.domain.model.entities.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends MongoRepository<Alert, Long> {
    List<Alert> findAllBySupplierId(Long supplierId);

    List<Alert> findAllByAdminRestaurantId(Long adminRestaurantId);

    Optional<Alert> findByOrderId(Long orderId);
}
