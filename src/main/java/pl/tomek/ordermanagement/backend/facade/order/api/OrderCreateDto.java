package pl.tomek.ordermanagement.backend.facade.order.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderCreate;

import java.time.LocalDate;
import java.util.List;

public record OrderCreateDto(@NotNull(message = "Order Date is mandatory")
                             LocalDate orderDate,
                             @Valid
                             @NotNull(message = "Customer is mandatory")
                             CustomerDto customer,
                             @Valid
                             @NotNull(message = "Shipping Address is mandatory")
                             AddressDto shippingAddress,
                             @Valid
                             @NotEmpty(message = "At least one Order Item is mandatory")
                             List<OrderItemCreateDto> orderItems) {
    public OrderCreate toDomainCreate() {
        return new OrderCreate(
                orderDate,
                customer.id(),
                shippingAddress.id()
        );
    }

}
