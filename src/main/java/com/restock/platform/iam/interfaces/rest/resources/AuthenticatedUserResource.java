package com.restock.platform.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, Long roleId, int subscription, String token) {

}