package pl.tomek.ordermanagement.backend.facade.customer.api;

import pl.tomek.ordermanagement.backend.feature.order.api.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CustomerOrderDto(UUID id,
                               LocalDate orderDate,
                               BigDecimal orderValue) {

    public static CustomerOrderDto of(Order order, BigDecimal orderValue) {
        return new CustomerOrderDto(
                order.id(),
                order.orderDate(),
                orderValue
        );
    }
}
