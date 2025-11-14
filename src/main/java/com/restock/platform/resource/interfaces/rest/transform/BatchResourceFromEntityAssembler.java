package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.iam.interfaces.rest.resources.UserResource;
import com.restock.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.restock.platform.resource.domain.model.aggregates.Batch;
import com.restock.platform.resource.interfaces.rest.resources.BatchResource;
import com.restock.platform.resource.interfaces.rest.resources.CustomSupplyResource;

public class BatchResourceFromEntityAssembler {

    public static BatchResource toResourceFromEntity(Batch batch) {
        CustomSupplyResource customSupplyResource = null;

        if (batch.getCustomSupply() != null) {
            customSupplyResource = CustomSupplyResourceFromEntityAssembler
                    .toResourceFromEntity(batch.getCustomSupply());
        }


        return new BatchResource(
                batch.getId(),
                batch.getUserId(),
                batch.getUserRoleId(),
                batch.getCustomSupplyId(),
                batch.getStock(),
                batch.getExpirationDate(),
                customSupplyResource
        );
    }
}
