package pl.tomek.ordermanagement.feature.order.api;

import java.time.LocalDate;
import java.util.UUID;

public record Order(UUID id,
                    LocalDate orderDate,
                    UUID customerId,
                    UUID shippingAddressId) {
}
