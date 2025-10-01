package com.restock.platform.profile.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Profile {
    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private String email;
    private String phone;
    private String address;
    private String country;
    private String avatar;

    @Field("business_name")
    private String businessName;

    @Field("business_address")
    private String businessAddress;
    private String description;
    private List<BusinessCategoryItem> businessCategories = new ArrayList<>();

    public Profile() {
    }

    public void clear() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;
        this.address = null;
        this.country = null;
        this.avatar = null;
        this.businessName = null;
        this.businessAddress = null;
        this.description = null;
        if (this.businessCategories != null) {
            this.businessCategories.clear();
        } else {
            this.businessCategories = new ArrayList<>();
        }
    }
}
