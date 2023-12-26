package pl.tomek.ordermanagement.feature.address.api;

import java.util.UUID;

public interface AddressService {
    Address create(AddressCreate addressCreate);

    void delete(UUID id);

    Address getById(UUID id);
}
