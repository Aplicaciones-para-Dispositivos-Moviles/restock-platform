package com.restock.platform.profile.domain.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "business_categories")
public class BusinessCategory {
    @Id
    private String id;

    @Field("business_category_name")
    private String name;

    public BusinessCategory(String name) {
        this.name = name;
    }
}
