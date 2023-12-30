package pl.tomek.ordermanagement.backend.facade.customer.api;

import java.time.LocalDate;
import java.util.Set;

public interface CustomerFacadeService {
    CustomerDto saveCustomer(CustomerDto customerDto);

    void deleteCustomer(CustomerDto customerDto);

    Set<CustomerDto> getAllCustomers();

    Set<CustomerDto> getCustomersByOrderDateRange(LocalDate startDate, LocalDate endDate);
}
