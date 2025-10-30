package com.restock.platform.monitoring.domain.model.queries;

import com.restock.platform.monitoring.domain.model.valueobjects.SaleCode;

public record GetAllSalesByCodeQuery(SaleCode saleCode) {
}
