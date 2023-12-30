package pl.tomek.ordermanagement.backend.feature.customer.api;

import jakarta.validation.Valid;

import java.util.Set;
import java.util.UUID;

public interface CustomerService {
    Customer create(@Valid CustomerCreate customerCreate);

    void delete(UUID id);

    Customer getById(UUID id);

    Set<Customer> getAll();
}
