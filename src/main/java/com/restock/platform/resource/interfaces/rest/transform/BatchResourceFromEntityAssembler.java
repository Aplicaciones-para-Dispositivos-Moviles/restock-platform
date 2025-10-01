package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.interfaces.rest.resources.BatchResource;

public class BatchResourceFromEntityAssembler {

    public static BatchResource toResourceFromEntity(Batch batch) {
        return new BatchResource(
                batch.getId(),
                batch.getUserId(),
                batch.getCustomSupplyId(),
                batch.getStock(),
                batch.getExpirationDate()
        );
    }
}
