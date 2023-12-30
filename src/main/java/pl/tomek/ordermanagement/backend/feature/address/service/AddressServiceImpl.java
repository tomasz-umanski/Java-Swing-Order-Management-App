package pl.tomek.ordermanagement.backend.feature.address.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.feature.address.api.Address;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressService;
import pl.tomek.ordermanagement.backend.feature.address.exception.AddressCreateValidatorException;
import pl.tomek.ordermanagement.backend.feature.validation.ObjectsValidator;

import java.util.Set;
import java.util.UUID;

@Service
class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ObjectsValidator<AddressCreate> validator;

    @Autowired
    AddressServiceImpl(AddressRepository addressRepository,
                       ObjectsValidator<AddressCreate> validator) {
        this.addressRepository = addressRepository;
        this.validator = validator;
    }

    @Override
    public Address create(AddressCreate addressCreate) {
        Set<String> violations = validator.validate(addressCreate);
        if (!violations.isEmpty()) {
            throw new AddressCreateValidatorException(violations);
        }
        AddressEntity addressEntity = AddressEntity.of(addressCreate);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
        return savedAddressEntity.toDomain();
    }

    @Override
    public void delete(UUID id) {
        addressRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Address getById(UUID id) {
        return addressRepository.getReferenceById(id).toDomain();
    }
}
