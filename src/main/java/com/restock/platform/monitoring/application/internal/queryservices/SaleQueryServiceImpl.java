package com.restock.platform.monitoring.application.internal.queryservices;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesByCodeQuery;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdQuery;
import com.restock.platform.monitoring.domain.services.SaleQueryService;
import com.restock.platform.monitoring.infrastructure.persistence.mongodb.repositories.SaleRepository;
import com.restock.platform.planning.infrastructure.persistence.mongodb.repositories.RecipeRepository;
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
    public List<Sale> handle(GetAllSalesByCodeQuery query) {
        return saleRepository.findBySaleCode(query.saleCode());
    }

    @Override
    public List<Sale> handle(GetAllSalesQuery query) {
        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> handle(GetSaleByIdQuery query) {
        return saleRepository.findById(query.saleId());
    }
}
