package com.restock.platform.monitoring.application.internal.queryservices;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesQuery;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesByUserIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdAndUserIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByNumberQuery;
import com.restock.platform.monitoring.domain.services.SaleQueryService;
import com.restock.platform.monitoring.infrastructure.persistence.mongodb.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {
    private final SaleRepository saleRepository;

    public SaleQueryServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> handle(GetAllSalesQuery query) {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> handle(GetAllSalesByUserIdQuery query) {
        return saleRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Sale> handle(GetSaleByIdQuery query) {
        return saleRepository.findById(query.saleId());
    }

    @Override
    public Optional<Sale> handle(GetSaleByIdAndUserIdQuery query) {
        return saleRepository.findByIdAndUserId(query.saleId(), query.userId());
    }

    @Override
    public Optional<Sale> handle(GetSaleByNumberQuery query) {
        return saleRepository.findBySaleNumber_Value(query.saleNumber());
    }
}
