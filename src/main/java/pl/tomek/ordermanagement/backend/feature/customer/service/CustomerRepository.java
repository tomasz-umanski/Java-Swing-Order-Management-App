package pl.tomek.ordermanagement.backend.feature.customer.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}