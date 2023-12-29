package pl.tomek.ordermanagement.facade.customer.api;

import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;

import java.util.UUID;

public record CustomerDto(UUID id,
                          String name,
                          String lastName,
                          String companyName,
                          String taxIdNumber,
                          AddressDto homeAddress,
                          AddressDto shippingAddress) {

    public static CustomerDto of(Customer customer, Address homeAddress, Address shippingAddress) {
        return new CustomerDto(
                customer.id(),
                customer.name(),
                customer.lastName(),
                customer.companyName(),
                customer.taxIdNumber(),
                AddressDto.of(homeAddress),
                (shippingAddress != null) ? AddressDto.of(shippingAddress) : null
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