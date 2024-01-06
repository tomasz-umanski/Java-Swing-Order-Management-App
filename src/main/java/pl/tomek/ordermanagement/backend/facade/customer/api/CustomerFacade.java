package pl.tomek.ordermanagement.backend.facade.customer.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CustomerFacade {
    CustomerDto saveCustomer(CustomerCreateDto customerCreateDto);

    void deleteCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    List<AddressDto> getCustomersAddresses(UUID customerId);

    List<CustomerDto> getCustomersWithFilteredOrders(LocalDate startDate,
                                                     LocalDate endDate,
                                                     BigDecimal minValue);
}
