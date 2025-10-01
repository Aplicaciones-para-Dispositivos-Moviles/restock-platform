package com.restock.platform.resource.application.internal.queryservices;

import com.restock.platform.resource.domain.model.aggregates.OrderBatch;
import com.restock.platform.resource.domain.model.queries.GetAllBatchesByOrderId;
import com.restock.platform.resource.domain.services.OrderBatchQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.OrderBatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBatchQueryServiceImpl implements OrderBatchQueryService {

    private final OrderBatchRepository orderBatchRepository;

    public OrderBatchQueryServiceImpl(OrderBatchRepository orderBatchRepository) {
        this.orderBatchRepository = orderBatchRepository;
    }

    @Override
    public List<OrderBatch> handle(GetAllBatchesByOrderId query) {
        return orderBatchRepository.findByOrderId(query.orderId());
    }
}
