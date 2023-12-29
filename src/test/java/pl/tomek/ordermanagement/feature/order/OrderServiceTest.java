package pl.tomek.ordermanagement.feature.order;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.feature.order.api.Order;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;
import pl.tomek.ordermanagement.feature.order.api.OrderService;
import pl.tomek.ordermanagement.feature.order.exception.OrderCreateValidatorException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService service;

    @Test
    void shouldSaveAssetAndAssignAnId() {
        OrderCreate orderCreate = mockOrderCreate();
        Order createdOrder = service.create(orderCreate);
        assertNotNull(createdOrder.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        OrderCreate orderCreate = mockOrderCreate();
        Order createdOrder = service.create(orderCreate);
        assertNotNull(createdOrder.id());

        Order retrievedOrder = service.getById(createdOrder.id());
        assertNotNull(retrievedOrder);
        assertEquals(retrievedOrder.customerId(), orderCreate.customerId());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        OrderCreate orderCreate = mockOrderCreate();
        service.create(orderCreate);
        service.create(orderCreate);

        Set<Order> retrievedOrderSet = service.getAll();

        assertNotNull(retrievedOrderSet);
        assertEquals(retrievedOrderSet.size(), 2);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        OrderCreate orderCreate = mockOrderCreate();
        Order createdOrder = service.create(orderCreate);
        assertNotNull(createdOrder.id());

        service.delete(createdOrder.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdOrder.id()));
    }

    @Test
    void shouldThrowValidatorException() {
        OrderCreate orderCreate = mockOrderCreateWithoutOrderDate();
        assertThrows(OrderCreateValidatorException.class, () -> service.create(orderCreate));
    }

    private OrderCreate mockOrderCreate() {
        return new OrderCreate(
                new Date(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    private OrderCreate mockOrderCreateWithoutOrderDate() {
        return new OrderCreate(
                null,
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }
}