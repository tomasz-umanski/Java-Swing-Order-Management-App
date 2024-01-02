package pl.tomek.ordermanagement.backend.facade.customer.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerCreate;

import java.util.UUID;

@Valid
public record CustomerDto(UUID id,
                          @NotBlank(message = "Name is mandatory")
                          String name,
                          @NotBlank(message = "Last Name is mandatory")
                          String lastName,
                          String companyName,
                          String taxIdNumber,
                          @Valid
                          AddressDto homeAddress,
                          @Valid
                          AddressDto shippingAddress) {

    public static CustomerDto of(Customer customer, AddressDto homeAddress, AddressDto shippingAddress) {
        return new CustomerDto(
                customer.id(),
                customer.name(),
                customer.lastName(),
                customer.companyName(),
                customer.taxIdNumber(),
                homeAddress,
                shippingAddress
        );
    }

    public CustomerCreate toCreate(UUID homeAddressId, UUID shippingAddressId) {
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