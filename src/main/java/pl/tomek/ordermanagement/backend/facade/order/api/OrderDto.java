package pl.tomek.ordermanagement.backend.facade.order.api;

import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.feature.order.api.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record OrderDto(UUID id,
                       LocalDate orderDate,
                       CustomerDto customer,
                       AddressDto shippingAddress,
                       Set<OrderItemDto> orderItems,
                       BigDecimal orderValue) {

    public static OrderDto of(Order order, AddressDto addressDto, Set<OrderItemDto> orderItemsDto, CustomerDto customerDto) {
        BigDecimal orderValue = orderItemsDto.stream()
                .map(orderItem -> orderItem.grossPrice().multiply(orderItem.quantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderDto(
                order.id(),
                order.orderDate(),
                customerDto,
                addressDto,
                orderItemsDto,
                orderValue
        );
    }
}
