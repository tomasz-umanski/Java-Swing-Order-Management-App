package pl.tomek.ordermanagement.backend.feature.customer.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerCreate(@NotBlank(message = "Name is mandatory")
                             String name,
                             @NotBlank(message = "LastName is mandatory")
                             String lastName,
                             String companyName,
                             String taxIdNumber,
                             @NotNull(message = "HomeAddressId is mandatory")
                             UUID homeAddressId,
                             UUID shippingAddressId) {
}