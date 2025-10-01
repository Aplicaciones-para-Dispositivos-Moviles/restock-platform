package com.restock.platform.iam.domain.model.aggregates;

import com.restock.platform.iam.domain.model.entities.Role;
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

    private Profile profile = new Profile();

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.profile = new Profile();
    }

    public String getRoleName() {
        return role != null ? role.getStringName() : null;
    }
}
