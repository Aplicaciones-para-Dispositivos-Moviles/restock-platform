package com.restock.platform.monitoring.infrastructure.persistence.mongodb.repositories;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends MongoRepository<Sale, Long> {
    Optional<Sale> findBySaleNumber_Value(String saleNumber);

    // Obtener la última venta ordenada por ID descendente
    Optional<Sale> findFirstByOrderByIdDesc();

    // Buscar todas las ventas de un usuario específico
    List<Sale> findByUserId(Integer userId);

    // Buscar una venta por ID y userId (para verificar permisos)
    Optional<Sale> findByIdAndUserId(Long id, Integer userId);

    long countBy();
}
