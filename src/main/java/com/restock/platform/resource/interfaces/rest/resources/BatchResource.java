package com.restock.platform.resource.interfaces.rest.resources;

import com.restock.platform.iam.interfaces.rest.resources.UserResource;

import java.time.LocalDate;

public record BatchResource(
        Long id,
        Long userId,
        Long userRoleId,
        Long customSupplyId,
        double stock,
        LocalDate expirationDate,
        CustomSupplyResource customSupply
) {}
