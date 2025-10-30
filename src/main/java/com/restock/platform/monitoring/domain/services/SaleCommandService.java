package com.restock.platform.monitoring.domain.services;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.commands.AddSaleItemToSaleCommand;
import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;

import java.util.Optional;

public interface SaleCommandService {
    Long handle(CreateSaleCommand command);
    Optional<Sale> handle(AddSaleItemToSaleCommand command);
}
