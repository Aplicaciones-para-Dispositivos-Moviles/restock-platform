package com.restock.platform.resource.interfaces.rest.transform;

import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.entities.Alert;
import com.restock.platform.resource.interfaces.rest.resources.AlertResource; // Asumiendo que has actualizado este DTO
import com.restock.platform.resource.interfaces.rest.resources.OrderBatchItemResource;
import com.restock.platform.resource.interfaces.rest.resources.OrderResource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AlertResourceFromEntityAssembler {

    /**
     * Convierte la entidad de dominio Alert a su Resource DTO, usando la estructura
     * del modelo de dominio proporcionado.
     * @param alert La entidad Alert.
     * @return El DTO AlertResource.
     */
    public AlertResource toResource(Alert alert) {
        if (alert == null) {
            return null;
        }


        return new AlertResource(
                alert.getId(),
                alert.getMessage(),
                alert.getOrderId(),
                alert.getDate(), // LocalDate
                alert.getSupplierId(),
                alert.getAdminRestaurantId(),
                alert.getSituationDescription()
        );
    }

}