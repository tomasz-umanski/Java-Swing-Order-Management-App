package pl.tomek.ordermanagement.feature.orderItem;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemCreate;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.feature.orderItem.exception.OrderItemCreateValidatorException;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderItemServiceTest {
    @Autowired
    private OrderItemService service;

    @Test
    void shouldSaveAssetAndAssignAnId() {
        OrderItemCreate orderItemCreate = mockOrderItemCreate();
        OrderItem createdOrderItem = service.create(orderItemCreate);
        assertNotNull(createdOrderItem.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        OrderItemCreate orderItemCreate = mockOrderItemCreate();
        OrderItem createdOrderItem = service.create(orderItemCreate);
        assertNotNull(createdOrderItem.id());

        OrderItem retrievedOrderItem = service.getById(createdOrderItem.id());
        assertNotNull(retrievedOrderItem);
        assertEquals(retrievedOrderItem.orderId(), orderItemCreate.orderId());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        OrderItemCreate orderItemCreate = mockOrderItemCreate();
        service.create(orderItemCreate);
        service.create(orderItemCreate);

        Set<OrderItem> retrievedOrderItemSet = service.getAll();

        assertNotNull(retrievedOrderItemSet);
        assertEquals(retrievedOrderItemSet.size(), 2);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        OrderItemCreate orderItemCreate = mockOrderItemCreate();
        OrderItem createdOrderItem = service.create(orderItemCreate);
        assertNotNull(createdOrderItem.id());

        service.delete(createdOrderItem.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdOrderItem.id()));
    }

    @Test
    void shouldThrowValidatorException() {
        OrderItemCreate orderItemCreate = mockOrderItemCreateWithoutOrderId();
        assertThrows(OrderItemCreateValidatorException.class, () -> service.create(orderItemCreate));
    }

    private OrderItemCreate mockOrderItemCreate() {
        return new OrderItemCreate(
                UUID.randomUUID(),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(123)
        );
    }

    private OrderItemCreate mockOrderItemCreateWithoutOrderId() {
        return new OrderItemCreate(
                null,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(123)
        );
    }
}
