package pl.tomek.ordermanagement.feature.order;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.feature.order.api.Order;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;
import pl.tomek.ordermanagement.feature.order.api.OrderService;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService service;

    private static final OrderCreate orderCreate = new OrderCreate(
            new Date(),
            UUID.randomUUID(),
            UUID.randomUUID()
    );

    @Test
    void shouldSaveAssetAndAssignAnId() {
        Order createdOrder = service.create(orderCreate);
        assertNotNull(createdOrder.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        Order createdOrder = service.create(orderCreate);
        assertNotNull(createdOrder.id());

        Order retrievedOrder = service.getById(createdOrder.id());
        assertNotNull(retrievedOrder);
        assertEquals(retrievedOrder.customerId(), orderCreate.customerId());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        service.create(orderCreate);
        service.create(orderCreate);

        Set<Order> retrievedOrderSet = service.getAll();

        assertNotNull(retrievedOrderSet);
        assertEquals(retrievedOrderSet.size(), 2);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        Order createdOrder = service.create(orderCreate);
        assertNotNull(createdOrder.id());

        service.delete(createdOrder.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdOrder.id()));
    }
}