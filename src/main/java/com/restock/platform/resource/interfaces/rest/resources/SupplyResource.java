package com.restock.platform.resource.interfaces.rest.resources;

import java.io.Serializable;

public record SupplyResource(
        Long id,
        String name,
        String description,
        boolean perishable,
        String category
) implements Serializable {}
