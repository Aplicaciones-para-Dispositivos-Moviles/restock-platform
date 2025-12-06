package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.commands.UpdateAlertCommand;
import com.restock.platform.resource.interfaces.rest.resources.UpdateAlertResource;

public class UpdateAlertCommandFromResourceAssembler {

    public static UpdateAlertCommand toCommandFromResource(UpdateAlertResource resource) {
        return new UpdateAlertCommand(
                resource.alertId(),
                resource.newState(),
                resource.newSituation()
        );
    }
}
