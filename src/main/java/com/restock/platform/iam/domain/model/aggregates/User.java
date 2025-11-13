package com.restock.platform.iam.domain.model.aggregates;

import com.restock.platform.iam.domain.model.entities.Role;
import com.restock.platform.iam.domain.model.valueobjects.SubscriptionType;
import com.restock.platform.profile.domain.model.entities.Profile;
import com.restock.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Document(collection = "users")
@Getter
@Setter
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 50)
    @Indexed(unique = true)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @DBRef
    private Role role;

    private Profile profile = Profile.defaultProfile();

    private int subscription = SubscriptionType.NO_SUBSCRIPTION.getValue();

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.profile = Profile.defaultProfile();
        this.subscription = SubscriptionType.NO_SUBSCRIPTION.getValue();
    }

    public String getRoleName() {
        return role != null ? role.getStringName() : null;
    }

    /**
     * Update user subscription
     * @param subscriptionValue the subscription value (0, 1, or 2)
     */
    public void updateSubscription(int subscriptionValue) {
        // Validate the subscription value
        SubscriptionType.fromValue(subscriptionValue);
        this.subscription = subscriptionValue;
    }

    /**
     * Get the subscription type
     * @return the subscription type
     */
    public SubscriptionType getSubscriptionType() {
        return SubscriptionType.fromValue(this.subscription);
    }
}
