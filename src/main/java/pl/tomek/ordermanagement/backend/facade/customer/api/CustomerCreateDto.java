package pl.tomek.ordermanagement.backend.facade.customer.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerCreate;

import java.util.UUID;

public record CustomerCreateDto(@NotBlank(message = "Name is mandatory")
                                String name,
                                @NotBlank(message = "Last Name is mandatory")
                                String lastName,
                                String companyName,
                                String taxIdNumber,
                                @Valid
                                AddressCreateDto homeAddress,
                                @Valid
                                AddressCreateDto shippingAddress) {
    public CustomerCreate toDomainCreate(UUID homeAddressId, UUID shippingAddressId) {
        return new CustomerCreate(
                this.name(),
                this.lastName(),
                this.companyName(),
                this.taxIdNumber(),
                homeAddressId,
                shippingAddressId
        );
    }
}