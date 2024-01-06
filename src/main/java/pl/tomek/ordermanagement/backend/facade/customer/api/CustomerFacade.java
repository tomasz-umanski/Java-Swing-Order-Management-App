package pl.tomek.ordermanagement.backend.facade.customer.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerFacade {
    CustomerDto saveCustomer(CustomerCreateDto customerCreateDto);

    void deleteCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    Set<CustomerDto> getCustomersByOrderDateRange(LocalDate startDate, LocalDate endDate);

    List<AddressDto> getCustomersAddresses(UUID customerId);
}
