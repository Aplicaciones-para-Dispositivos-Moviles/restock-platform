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
        this.applyDefaultValues();
    }

    public static Profile defaultProfile() {
        return new Profile();
    }

    private void applyDefaultValues() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
        this.address = "";
        this.country = "";
        this.avatar = "";
        this.businessName = "";
        this.businessAddress = "";
        this.description = "";
        this.businessCategories = new ArrayList<>();
    }

    public void clear() {
        this.applyDefaultValues();
    }
}
