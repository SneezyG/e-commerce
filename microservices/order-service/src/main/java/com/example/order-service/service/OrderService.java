package com.example.order.service;

import com.example.order.event.EventPublisher;
import com.example.order.event.OrderCreatedEvent;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public void createOrder(Order order) {
        order.setStatus("CREATED");
        orderRepository.save(order);

        // Publish event
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getUserId(),
                order.getItems(),
                order.getTotalAmount()
        );
        eventPublisher.publishOrderCreated(event);
    }
}
