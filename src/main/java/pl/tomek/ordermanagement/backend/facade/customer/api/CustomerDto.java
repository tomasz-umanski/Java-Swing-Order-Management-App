package pl.tomek.ordermanagement.backend.facade.customer.api;

import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;

import java.util.List;
import java.util.UUID;

public record CustomerDto(UUID id,
                          String name,
                          String lastName,
                          String companyName,
                          String taxIdNumber,
                          AddressDto homeAddress,
                          AddressDto shippingAddress,
                          List<CustomerOrderDto> customerOrderDtoList) {
    @Override
    public String toString() {
        return name + " " + lastName;
    }

    public static CustomerDto of(Customer customer, AddressDto homeAddress, AddressDto shippingAddress, List<CustomerOrderDto> customerOrderDtoList) {
        return new CustomerDto(
                customer.id(),
                customer.name(),
                customer.lastName(),
                customer.companyName(),
                customer.taxIdNumber(),
                homeAddress,
                shippingAddress,
                customerOrderDtoList
        );
    }
}