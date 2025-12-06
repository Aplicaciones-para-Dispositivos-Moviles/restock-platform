package com.restock.platform.resource.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Resource to return Alert information to the client (Supplier or Restaurant).
 */
public record AlertResource(
        Long id,
        String message,
        Long orderId,
        LocalDate date, // Campo añadido
        Long supplierId,
        Long adminRestaurantId, // ID del administrador del restaurante (cliente)
        String situationDescription // Usando la descripción limpia del estado
) {}