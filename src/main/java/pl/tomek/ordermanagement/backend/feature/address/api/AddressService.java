package pl.tomek.ordermanagement.backend.feature.address.api;

import jakarta.validation.Valid;

import java.util.UUID;

public interface AddressService {
    Address create(@Valid AddressCreate addressCreate);

    void delete(UUID id);

    Address getById(UUID id);
}
