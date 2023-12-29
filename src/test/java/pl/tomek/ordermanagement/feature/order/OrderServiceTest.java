package pl.tomek.ordermanagement.feature.order;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.annotation.UnitTest;
import pl.tomek.ordermanagement.feature.order.api.OrderDto;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;
import pl.tomek.ordermanagement.feature.order.api.OrderService;
import pl.tomek.ordermanagement.feature.order.exception.OrderCreateValidatorException;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class OrderServiceTest {

    @Autowired
    private OrderService service;

    @Test
    void shouldSaveAssetAndAssignAnId() {
        OrderCreate orderCreate = mockOrderCreate();
        OrderDto createdOrderDto = service.create(orderCreate);
        assertNotNull(createdOrderDto.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        OrderCreate orderCreate = mockOrderCreate();
        OrderDto createdOrderDto = service.create(orderCreate);
        assertNotNull(createdOrderDto.id());

        OrderDto retrievedOrderDto = service.getById(createdOrderDto.id());
        assertNotNull(retrievedOrderDto);
        assertEquals(retrievedOrderDto.customerId(), orderCreate.customerId());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        OrderCreate orderCreate = mockOrderCreate();
        OrderCreate secondOrderCreate = mockOrderCreate();
        service.create(orderCreate);
        service.create(secondOrderCreate);

        Set<OrderDto> retrievedOrderSetDto = service.getAll();

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

        Set<OrderDto> retrievedOrderSetDto = service.get(startDate, endDate);

        assertNotNull(retrievedOrderSetDto);
        assertEquals(retrievedOrderSetDto.size(), 1);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        OrderCreate orderCreate = mockOrderCreate();
        OrderDto createdOrderDto = service.create(orderCreate);
        assertNotNull(createdOrderDto.id());

        service.delete(createdOrderDto.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdOrderDto.id()));
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