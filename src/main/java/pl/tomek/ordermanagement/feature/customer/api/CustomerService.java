package pl.tomek.ordermanagement.feature.customer.api;

import java.util.Set;
import java.util.UUID;

public interface CustomerService {
    Customer create(CustomerCreate customerCreate);

    void delete(UUID id);

    Customer getById(UUID id);

    Set<Customer> getAll();
}
