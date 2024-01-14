package pl.tomek.ordermanagement.backend.facade.customer.api;

import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CustomerDto(UUID id,
                          String name,
                          String lastName,
                          String companyName,
                          String taxIdNumber,
                          AddressDto homeAddress,
                          AddressDto shippingAddress,
                          List<CustomerOrderDto> customerOrderDtoList,
                          BigDecimal ordersValue) {
    @Override
    public String toString() {
        return name + " " + lastName;
    }

    public static CustomerDto of(Customer customer, AddressDto homeAddress, AddressDto shippingAddress, List<CustomerOrderDto> customerOrderDtoList) {
        BigDecimal ordersValue = BigDecimal.valueOf(0.0);
        if (customerOrderDtoList != null) {
            ordersValue = customerOrderDtoList.stream()
                    .map(CustomerOrderDto::orderValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return new CustomerDto(
                customer.id(),
                customer.name(),
                customer.lastName(),
                customer.companyName(),
                customer.taxIdNumber(),
                homeAddress,
                shippingAddress,
                customerOrderDtoList,
                ordersValue
        );
    }
}