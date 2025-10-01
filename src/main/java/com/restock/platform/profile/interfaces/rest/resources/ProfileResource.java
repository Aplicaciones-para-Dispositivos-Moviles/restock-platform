package com.restock.platform.profile.interfaces.rest.resources;

import java.util.List;

public record ProfileResource(Long userId,
                              String firstName,
                              String lastName,
                              String email,
                              String phone,
                              String address,
                              String country,
                              String avatar,
                              String businessName,
                              String businessAddress,
                              String description,
                              List<BusinessCategoryResource> businessCategories) {
}
