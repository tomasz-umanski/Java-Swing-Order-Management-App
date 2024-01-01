package pl.tomek.ordermanagement.backend.facade.customer.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CustomerFacade {
    CustomerDto saveCustomer(CustomerDto customerDto);

    void deleteCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    Set<CustomerDto> getCustomersByOrderDateRange(LocalDate startDate, LocalDate endDate);
}
