package com.restock.platform.resource.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.resource.domain.model.aggregates.OrderBatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBatchRepository extends MongoRepository<OrderBatch, Long> {

    /**
     * Finds all order batch entries by the given order ID.
     *
     * @param orderId ID of the order to filter by
     * @return list of matching OrderBatch entities
     */
    List<OrderBatch> findByOrderId(Long orderId);
}
