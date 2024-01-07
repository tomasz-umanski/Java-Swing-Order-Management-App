package pl.tomek.ordermanagement.backend.feature.order;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.backend.feature.order.api.Order;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderCreate;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.order.exception.OrderCreateValidatorException;
import pl.tomek.ordermanagement.utils.BaseTest;
import pl.tomek.ordermanagement.utils.UnitTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class OrderServiceTest extends BaseTest {

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
        OrderCreate secondOrderCreate = mockOrderCreate();
        service.create(orderCreate);
        service.create(secondOrderCreate);

        Set<Order> retrievedOrderSetDto = service.getAll();

        assertNotNull(retrievedOrderSetDto);
        assertEquals(retrievedOrderSetDto.size(), 2);
    }

    @Test
    void shouldSaveAssetsAndRetrieveOnlyInBetweenDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        OrderCreate orderCreate = mockOrderCreateIn2023();
        OrderCreate secondOrderCreate = mockOrderCreateIn2022();
        service.create(orderCreate);
        service.create(secondOrderCreate);

        List<Order> retrievedOrderSetDto = service.get(startDate, endDate);

        assertNotNull(retrievedOrderSetDto);
        assertEquals(retrievedOrderSetDto.size(), 1);
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
                LocalDate.now(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    private OrderCreate mockOrderCreateIn2023() {
        return new OrderCreate(
                LocalDate.of(2023, 10, 10),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    private OrderCreate mockOrderCreateIn2022() {
        return new OrderCreate(
                LocalDate.of(2022, 10, 10),
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