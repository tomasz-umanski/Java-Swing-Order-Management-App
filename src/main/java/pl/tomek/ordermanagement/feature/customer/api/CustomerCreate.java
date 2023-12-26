package pl.tomek.ordermanagement.feature.customer.api;

import java.util.UUID;

public record CustomerCreate(String name,
                             String lastName,
                             String companyName,
                             String taxIdNumber,
                             UUID homeAddressId,
                             UUID shippingAddressId) {
}