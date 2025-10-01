package com.restock.platform.profile.domain.model.commands;

public record UpdatePersonalInformationCommand(Long userId,
                                                String firstName,
                                                String lastName,
                                                String email,
                                                String phone,
                                                String address,
                                                String country,
                                                String avatar) {
}
