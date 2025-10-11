package com.restock.platform.resource.application.internal.queryservices;
import com.restock.platform.resource.domain.model.aggregates.Order;
import com.restock.platform.resource.domain.model.queries.*;
import com.restock.platform.resource.domain.services.OrderQueryService;
import com.restock.platform.resource.infrastructure.persistence.mongodb.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderRepository orderRepository;
    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public List<Order> handle(GetAllOrdersQuery query) {
        return orderRepository.findAll();
    }
    @Override
    public Optional<Order> handle(GetOrderByIdQuery query){return orderRepository.findById(query.id());};
}
