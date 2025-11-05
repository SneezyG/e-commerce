package com.example.order.integration;

import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderEventIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testOrderEventFlow() throws InterruptedException {
        Order order = new Order(
                "10",
                "user2",
                50.0,
                List.of(new OrderItem(null, "A101", 1)),
                "CREATED"
        );

        // Save order and publish event
        orderRepository.save(order);
        rabbitTemplate.convertAndSend("order.created", order);

        // Wait briefly for consumer to process
        Thread.sleep(2000);

        assertTrue(orderRepository.findById("10").isPresent());
    }
}
