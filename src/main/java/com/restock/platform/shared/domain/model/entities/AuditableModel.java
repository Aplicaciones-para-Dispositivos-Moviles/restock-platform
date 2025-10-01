package com.restock.platform.shared.domain.model.entities;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

public abstract class AuditableModel {
    @Id
    @Getter
    private Long id;

    @Getter
    @CreatedDate
    private Date createdAt;

    @Getter
    @LastModifiedDate
    private Date updatedAt;

    public void setId(Long id) {
        this.id = id;
    }
}