package pl.tomek.ordermanagement.facade.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.facade.customer.api.CustomerFacadeService;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.feature.order.api.OrderService;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CustomerFacadeServiceImpl implements CustomerFacadeService {
    private final AddressService addressService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @Autowired
    public CustomerFacadeServiceImpl(AddressService addressService,
                                     CustomerService customerService,
                                     OrderService orderService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Address homeAddress = createAddress(customerDto.homeAddress());
        Address shippingAddress = createAddress(customerDto.shippingAddress());
        Customer customer = createCustomer(customerDto, homeAddress, shippingAddress);
        return CustomerDto.of(customer, homeAddress, shippingAddress);
    }

    @Override
    public void deleteCustomer(CustomerDto customerDto) {
        deleteAddress(customerDto.homeAddress().id());
        deleteAddress(customerDto.shippingAddress().id());
        customerService.delete(customerDto.id());
    }

    @Override
    public Set<CustomerDto> getAllCustomers() {
        return customerService.getAll().stream()
                .map(customer -> {
                    Address homeAddress = getAddressById(customer.homeAddressId());
                    Address shippingAddress = getAddressById(customer.shippingAddressId());
                    return CustomerDto.of(customer, homeAddress, shippingAddress);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CustomerDto> getCustomersByOrderDateRange(LocalDate startDate, LocalDate endDate) {
        return orderService.get(startDate, endDate).stream()
                .map(order -> {
                    Customer customer = customerService.getById(order.customerId());
                    Address homeAddress = getAddressById(customer.homeAddressId());
                    Address shippingAddress = getAddressById(customer.shippingAddressId());
                    return CustomerDto.of(customer, homeAddress, shippingAddress);
                })
                .collect(Collectors.toSet());
    }

    private Address getAddressById(UUID addressId) {
        return (addressId != null) ? addressService.getById(addressId) : null;
    }

    private void deleteAddress(UUID addressId) {
        if (addressId != null) {
            addressService.delete(addressId);
        }
    }

    private Address createAddress(AddressDto addressDto) {
        if (addressDto != null) {
            AddressCreate addressCreate = addressDto.toCreate();
            return addressService.create(addressCreate);
        }
        return null;
    }

    private Customer createCustomer(CustomerDto customerDto, Address homeAddress, Address shippingAddress) {
        UUID shippingAddressId = (shippingAddress != null) ? shippingAddress.id() : null;
        CustomerCreate customerCreate = customerDto.toCreate(homeAddress.id(), shippingAddressId);
        return customerService.create(customerCreate);
    }
}
