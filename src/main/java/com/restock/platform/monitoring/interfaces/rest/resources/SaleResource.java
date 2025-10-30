package com.restock.platform.monitoring.interfaces.rest.resources;

import java.util.List;

// Lo que guardamos de un sale
public record SaleResource(
        Long id,
        String saleCode,
        Integer userId,
        Double totalPrice,
        List<SaleItemResource> items
) {
}
