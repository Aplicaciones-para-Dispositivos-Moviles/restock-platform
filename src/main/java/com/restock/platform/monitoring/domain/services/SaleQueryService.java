package com.restock.platform.monitoring.domain.services;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesQuery;
import com.restock.platform.monitoring.domain.model.queries.GetAllSalesByUserIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByIdAndUserIdQuery;
import com.restock.platform.monitoring.domain.model.queries.GetSaleByNumberQuery;

import java.util.List;
import java.util.Optional;

public interface SaleQueryService {
    List<Sale> handle(GetAllSalesQuery query);
    List<Sale> handle(GetAllSalesByUserIdQuery query);
    Optional<Sale> handle(GetSaleByIdQuery query);
    Optional<Sale> handle(GetSaleByIdAndUserIdQuery query);
    Optional<Sale> handle(GetSaleByNumberQuery query);
}
