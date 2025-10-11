package com.restock.platform.profile.interfaces.rest.resources;

public record UpdatePasswordResource(String currentPassword, String newPassword) {
}
