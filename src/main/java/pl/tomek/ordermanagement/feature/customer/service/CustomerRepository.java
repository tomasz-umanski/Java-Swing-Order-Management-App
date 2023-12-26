package pl.tomek.ordermanagement.feature.customer.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
//    Set<Customer> get(Date from, Date to);
//
//    Set<Customer> get(BigDecimal grossPrice);
//
//    Set<Customer> get(BigDecimal grossPrice, SortDirection sortDirection);
}