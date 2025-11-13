package com.restock.platform.subscriptions.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.subscriptions.domain.model.aggregates.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Subscription repository
 * <p>
 *     This interface represents the repository for the Subscription entity.
 * </p>
 */
@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, Long> {

    /**
     * Find subscription by user id
     * @param userId the user id
     * @return an optional containing the subscription if found
     */
    Optional<Subscription> findByUserId(Long userId);

    /**
     * Check if subscription exists by user id
     * @param userId the user id
     * @return true if subscription exists, false otherwise
     */
    boolean existsByUserId(Long userId);
}

