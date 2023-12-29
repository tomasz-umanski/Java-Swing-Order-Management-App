package pl.tomek.ordermanagement.facade.api;

import java.util.Date;
import java.util.Set;

public record OrderDto(Date orderDate,
                       CustomerDto customer,
                       AddressDto shippingAddress,
                       Set<OrderItemDto> orderItems) {
}
