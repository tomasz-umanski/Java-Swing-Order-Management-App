package pl.tomek.ordermanagement.feature.address.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;

import java.util.UUID;

@Service
class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(AddressCreate addressCreate) {
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
