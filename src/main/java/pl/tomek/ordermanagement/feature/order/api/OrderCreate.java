package pl.tomek.ordermanagement.feature.order.api;

import java.util.Date;
import java.util.UUID;

public record OrderCreate(Date orderDate,
                          UUID customerId,
                          UUID shippingAddressId) {
}
