package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.entities.Alert;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsByAdminRestaurantIdQuery;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsBySupplierIdQuery;
import com.restock.platform.resource.domain.model.queries.GetAllAlertsQuery;
import com.restock.platform.resource.domain.services.AlertQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertQueryServiceImpl implements AlertQueryService {
    private final AlertRepository alertRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public List<Alert> handle(GetAllAlertsBySupplierIdQuery query) {
        return alertRepository.findAllBySupplierId(query.supplierId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsByAdminRestaurantIdQuery query) {
        return alertRepository.findAllByAdminRestaurantId(query.adminRestaurantId());
    }
}
