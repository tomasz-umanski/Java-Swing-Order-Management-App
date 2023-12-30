package pl.tomek.ordermanagement.backend.feature.address.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
