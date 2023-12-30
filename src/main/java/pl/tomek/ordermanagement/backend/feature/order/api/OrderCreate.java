package pl.tomek.ordermanagement.backend.feature.order.api;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record OrderCreate(@NotNull(message = "OrderDate is mandatory")
                          LocalDate orderDate,
                          @NotNull(message = "CustomerId is mandatory")
                          UUID customerId,
                          @NotNull(message = "ShippingAddressId is mandatory")
                          UUID shippingAddressId) {
}
