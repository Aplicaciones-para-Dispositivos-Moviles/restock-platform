package com.restock.platform.shared.domain.model.aggregates;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Date;

/**
 * Abstract class for auditable aggregate roots.
 * It extends AbstractAggregateRoot and is annotated with auditing support for MongoDB.
 *
 * @param <T> the type of the aggregate root
 */
@Getter
public abstract class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {
    @Id
    private Long id;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    /**
     * Registers a domain event to be published.
     *
     * @param event the domain event to register
     */
    public void addDomainEvent(Object event) {
        registerEvent(event);
    }

    public void setId(Long id) {
        this.id = id;
    }
}