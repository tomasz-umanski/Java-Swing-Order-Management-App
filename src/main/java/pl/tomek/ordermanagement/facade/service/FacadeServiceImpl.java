package pl.tomek.ordermanagement.facade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.api.CustomerDto;
import pl.tomek.ordermanagement.facade.api.FacadeService;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;

@Service
class FacadeServiceImpl implements FacadeService {
    private final AddressService addressService;
    private final CustomerService customerService;

    @Autowired
    public FacadeServiceImpl(AddressService addressService, CustomerService customerService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        AddressCreate homeAddressCreate = customerDto.homeAddress().toCreate();
        Address homeAddress = addressService.create(homeAddressCreate);

        Address shippingAddress = null;

        if (customerDto.shippingAddress() != null) {
            AddressCreate shippingAddressCreate = customerDto.shippingAddress().toCreate();
            shippingAddress = addressService.create(shippingAddressCreate);
        }

        CustomerCreate customerCreate = customerDto.toCreate(
                homeAddress.id(),
                (shippingAddress != null) ? shippingAddress.id() : null);

        Customer customer = customerService.create(customerCreate);

        return CustomerDto.of(customer, homeAddress, shippingAddress);
    }
}
