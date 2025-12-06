package com.restock.platform.resource.domain.services;


import com.restock.platform.resource.domain.model.entities.Alert;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsByAdminRestaurantIdQuery;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsBySupplierIdQuery;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsQuery;

import java.util.List;

public interface AlertQueryService {
    List<Alert> handle(GetAllAlertsQuery query);
    List<Alert> handle(GetAllAlertsBySupplierIdQuery query);
    List<Alert> handle(GetAllAlertsByAdminRestaurantIdQuery query);
}
