package com.restock.platform.profile.interfaces.rest.resources;

public record UpdatePersonalInformationResource(String firstName,
                                                String lastName,
                                                String email,
                                                String phone,
                                                String address,
                                                String country,
                                                String avatar) {
}
