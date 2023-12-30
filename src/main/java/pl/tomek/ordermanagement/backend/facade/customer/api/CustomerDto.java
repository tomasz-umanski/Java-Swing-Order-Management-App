package pl.tomek.ordermanagement.backend.facade.customer.api;

import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerCreate;

import java.util.UUID;

public record CustomerDto(UUID id,
                          String name,
                          String lastName,
                          String companyName,
                          String taxIdNumber,
                          AddressDto homeAddress,
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