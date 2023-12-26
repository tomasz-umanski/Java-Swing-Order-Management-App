package pl.tomek.ordermanagement.feature.customer.api;

import java.util.UUID;

public record Customer(UUID id,
                       String name,
                       String lastName,
                       String companyName,
                       String taxIdNumber,
                       UUID homeAddressId,
                       UUID shippingAddressId) {
}