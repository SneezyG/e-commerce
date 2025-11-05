package com.example.order.service;

import com.example.order.event.EventPublisher;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private OrderService orderService;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderPublishesEvent() {
        Order order = new Order(
                "1",
                "user1",
                100.0,
                List.of(new OrderItem(null, "A101", 2)),
                "CREATED"
        );

        orderService.createOrder(order);

        verify(orderRepository, times(1)).save(order);
        verify(eventPublisher, times(1)).publishOrderCreated(any());
    }
}
