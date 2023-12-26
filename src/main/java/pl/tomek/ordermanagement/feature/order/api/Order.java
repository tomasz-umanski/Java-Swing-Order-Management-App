package pl.tomek.ordermanagement.feature.order.api;

import java.util.Date;
import java.util.UUID;

public record Order(UUID id,
                    Date orderDate,
                    UUID customerId,
                    UUID shippingAddressId) {
}
