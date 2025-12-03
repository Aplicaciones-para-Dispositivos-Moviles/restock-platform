package com.restock.platform.resource.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.resource.domain.model.aggregates.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    List<Order> findAllBySupplierId(Long supplierId);

    List<Order> findAllByAdminRestaurantId(Long adminRestaurantId);

}
