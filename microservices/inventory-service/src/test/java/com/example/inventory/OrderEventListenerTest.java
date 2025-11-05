package com.example.inventory.event;

import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.InventoryRepository;
import com.example.order.event.OrderCreatedEvent;
import com.example.order.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class OrderEventListenerTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OrderEventListener listener;

    public OrderEventListenerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleOrderCreated_reservesStock() {
        InventoryItem item = new InventoryItem("A101", 5);
        when(inventoryRepository.findById("A101")).thenReturn(Optional.of(item));

        OrderCreatedEvent event = new OrderCreatedEvent(
                "1",
                "user1",
                List.of(new OrderItem(null, "A101", 2)),
                100.0
        );

        listener.handleOrderCreated(event);

        verify(inventoryRepository, times(1)).save(any(InventoryItem.class));
    }
}
