package pl.tomek.ordermanagement.facade.order.api;

import pl.tomek.ordermanagement.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.facade.customer.api.CustomerDto;

import java.time.LocalDate;
import java.util.Set;

public record OrderDto(LocalDate orderDate,
                       CustomerDto customer,
                       AddressDto shippingAddress,
                       Set<OrderItemDto> orderItems) {
}
