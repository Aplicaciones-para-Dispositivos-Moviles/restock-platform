package com.restock.platform.resource.interfaces.rest.resources;

import java.time.LocalDate;

public record BatchResource(
        Long id,
        Long userId,
        Long customSupplyId,
        double stock,
        LocalDate expirationDate,
        CustomSupplyResource customSupply
) {}
