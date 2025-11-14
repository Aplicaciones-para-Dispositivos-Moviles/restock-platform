package com.restock.platform.resource.domain.model.aggregates;

import com.restock.platform.iam.domain.model.aggregates.User;
import com.restock.platform.resource.domain.model.commands.CreateBatchCommand;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Batch aggregate root.
 *
 * Represents a batch of supplies registered by a user.
 * A batch contains stock information and expiration date.
 */
@Document(collection = "batches")
public class Batch extends AuditableAbstractAggregateRoot<Batch> {

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private Long userRoleId;

    @Setter
    private Long customSupplyId;

    @Getter
    @Setter
    private Double stock;

    @Getter
    @Setter
    private LocalDate expirationDate;

    @Getter
    @Setter
    private CustomSupply customSupply;

    protected Batch() {
    }

    /**
     * Constructs a Batch with all its required data.
     *
     * @param userId The ID of the user who created the batch.
     * @param customSupply The supply entity this batch is associated with.
     * @param stock The amount of stock in the batch.
     * @param expirationDate The expiration date of the batch.
     */
    public Batch(Long userId, Long userRoleId, CustomSupply customSupply, Double stock, LocalDate expirationDate) {
        this.userId = userId;
        this.userRoleId = userRoleId;
        this.customSupplyId = customSupply != null ? customSupply.getId() : null;
        this.stock = stock;
        this.expirationDate = expirationDate;
    }

    /**
     * Constructs a Batch from a CreateBatchCommand.
     *
     * @param command The command containing batch creation data.
     * @param customSupply The supply associated with this batch.
     */
    public Batch(CreateBatchCommand command, Long userRoleId, CustomSupply customSupply) {
        this(command.userId(), userRoleId, customSupply, command.stock(), command.expirationDate());
    }

    public Long getCustomSupplyId() {
        return customSupplyId;
    }
    public Batch update(Double newStock, LocalDate newExpirationDate) {
        if (newStock != null) this.stock = newStock;
        if (newExpirationDate != null) this.expirationDate = newExpirationDate;
        return this;
    }
}
