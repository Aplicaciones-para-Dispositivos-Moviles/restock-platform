package com.restock.platform.resource.interfaces.rest.resources;

public record OrderBatchResource(Long id, Long orderId, Long batchId, Integer quantity, boolean accepted) {
}
