package pl.tomek.ordermanagement.feature.order.api;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record OrderCreate(@NotNull(message = "OrderDate is mandatory")
                          Date orderDate,
                          @NotNull(message = "CustomerId is mandatory")
                          UUID customerId,
                          @NotNull(message = "ShippingAddressId is mandatory")
                          UUID shippingAddressId) {
}
