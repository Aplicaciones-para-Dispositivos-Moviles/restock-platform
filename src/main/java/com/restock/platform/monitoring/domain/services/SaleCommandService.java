package com.restock.platform.monitoring.domain.services;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.domain.model.commands.DeleteSaleCommand;

import java.util.Optional;

public interface SaleCommandService {
    Long handle(CreateSaleCommand command);
    void handle(DeleteSaleCommand command);
}
