package pl.tomek.ordermanagement.feature.address.service;

import org.springframework.stereotype.Repository;
import pl.tomek.ordermanagement.feature.database.api.InMemoryRepository;

@Repository
interface AddressRepository extends InMemoryRepository<AddressEntity> {
}
