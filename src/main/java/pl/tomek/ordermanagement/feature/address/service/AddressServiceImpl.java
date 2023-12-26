package pl.tomek.ordermanagement.feature.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;

import java.util.UUID;

@Service
class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private final AddressRepository addressRepository;

    @Override
    public Address create(AddressCreate addressCreate) {
        AddressEntity addressEntity = AddressEntity.of(addressCreate);
        AddressEntity savedAddressEntity = addressRepository.create(addressEntity);
        return savedAddressEntity.toDomain();
    }

    @Override
    public void delete(UUID id) {
        addressRepository.delete(id);
    }

    @Override
    public Address getById(UUID id) {
        return addressRepository.getById(id).toDomain();
    }
}
