package com.example.inventory.event;

import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.InventoryRepository;
import com.example.order.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {

    private final InventoryRepository inventoryRepository;

    public OrderEventListener(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @RabbitListener(queues = "order.created")
    public void handleOrderCreated(OrderCreatedEvent event) {
        boolean success = true;

        // Check and reserve stock
        for (var item : event.getItems()) {
            InventoryItem inventoryItem = inventoryRepository.findById(item.getProductId()).orElse(null);
            if (inventoryItem == null || inventoryItem.getQuantity() < item.getQuantity()) {
                success = false;
                break;
            }
        }

        if (success) {
            for (var item : event.getItems()) {
                InventoryItem inventoryItem = inventoryRepository.findById(item.getProductId()).get();
                inventoryItem.setQuantity(inventoryItem.getQuantity() - item.getQuantity());
                inventoryRepository.save(inventoryItem);
            }
            System.out.println("Inventory reserved for order " + event.getOrderId());
        } else {
            System.out.println("Inventory not sufficient for order " + event.getOrderId());
        }
    }
}
