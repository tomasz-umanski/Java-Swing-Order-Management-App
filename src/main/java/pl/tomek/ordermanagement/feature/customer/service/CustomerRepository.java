package pl.tomek.ordermanagement.feature.customer.service;

import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.database.api.InMemoryRepository;
import pl.tomek.ordermanagement.model.SortDirection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

interface CustomerRepository extends InMemoryRepository<CustomerEntity> {
    default Set<Customer> getAll() {
        super.database;
    }

    Set<Customer> get(Date from, Date to);

    Set<Customer> get(BigDecimal grossPrice);

    Set<Customer> get(BigDecimal grossPrice, SortDirection sortDirection);
}