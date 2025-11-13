package com.restock.platform.iam.interfaces.rest.resources;

public record UserResource(Long id, String username, Long roleId, int subscription) {
}