package com.restock.platform.monitoring.domain.services;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesByCodeQuery;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SaleQueryService {
    List<Sale> handle(GetAllSalesByCodeQuery query);
    List<Sale> handle(GetAllSalesQuery query);
    Optional<Sale> handle(GetSaleByIdQuery query);
}
