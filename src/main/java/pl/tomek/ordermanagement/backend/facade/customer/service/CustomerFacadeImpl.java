package pl.tomek.ordermanagement.backend.facade.customer.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.facade.customer.api.*;
import pl.tomek.ordermanagement.backend.facade.customer.exception.CustomerCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressService;
import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.backend.validation.ObjectsValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CustomerFacadeImpl implements CustomerFacade {
    private final AddressService addressService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ObjectsValidator<CustomerCreateDto> validator;

    @Autowired
    public CustomerFacadeImpl(AddressService addressService,
                              CustomerService customerService,
                              OrderService orderService, OrderItemService orderItemService,
                              ObjectsValidator<CustomerCreateDto> validator) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public CustomerDto saveCustomer(CustomerCreateDto customerCreateDto) {
        Set<String> violations = validator.validate(customerCreateDto);
        if (!violations.isEmpty()) {
            throw new CustomerCreateDtoValidatorException(violations);
        }
        AddressDto homeAddress = createAddress(customerCreateDto.homeAddress());
        AddressDto shippingAddress = !customerCreateDto.homeAddress().equals(customerCreateDto.shippingAddress())
                ? createAddress(customerCreateDto.shippingAddress())
                : homeAddress;
        return createCustomer(customerCreateDto, homeAddress, shippingAddress);
    }

    private AddressDto createAddress(AddressCreateDto addressCreateDto) {
        if (addressCreateDto != null) {
            AddressCreate addressCreate = addressCreateDto.toDomainCreate();
            return AddressDto.of(addressService.create(addressCreate));
        }
        return null;
    }

    private CustomerDto createCustomer(CustomerCreateDto customerCreateDto, AddressDto homeAddress, AddressDto shippingAddress) {
        UUID shippingAddressId = (shippingAddress != null) ? shippingAddress.id() : null;
        CustomerCreate customerCreate = customerCreateDto.toDomainCreate(homeAddress.id(), shippingAddressId);
        return CustomerDto.of(customerService.create(customerCreate), homeAddress, shippingAddress);
    }


    @Override
    public void deleteCustomer(CustomerDto customerDto) {
        deleteAddressIfExists(customerDto.homeAddress());
        deleteAddressIfExists(customerDto.shippingAddress());
        deleteOrdersForCustomer(customerDto.id());
        customerService.delete(customerDto.id());
    }

    private void deleteOrdersForCustomer(UUID customerId) {
        orderService.get(customerId).forEach(order -> {
            deleteOrderItemForOrder(order.id());
            orderService.delete(order.id());
        });
    }

    private void deleteOrderItemForOrder(UUID orderId) {
        orderItemService.getByOrderId(orderId).forEach(orderItem -> orderItemService.delete(orderItem.id()));
    }

    private void deleteAddressIfExists(AddressDto addressDto) {
        if (addressDto != null) {
            addressService.delete(addressDto.id());
        }
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAll().stream()
                .map(customer -> {
                    AddressDto homeAddress = getAddressById(customer.homeAddressId());
                    AddressDto shippingAddress = getAddressById(customer.shippingAddressId());
                    return CustomerDto.of(customer, homeAddress, shippingAddress);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Set<CustomerDto> getCustomersByOrderDateRange(LocalDate startDate, LocalDate endDate) {
        return orderService.get(startDate, endDate).stream()
                .map(order -> {
                    Customer customer = customerService.getById(order.customerId());
                    AddressDto homeAddress = getAddressById(customer.homeAddressId());
                    AddressDto shippingAddress = getAddressById(customer.shippingAddressId());
                    return CustomerDto.of(customer, homeAddress, shippingAddress);
                })
                .collect(Collectors.toSet());
    }

    private AddressDto getAddressById(UUID addressId) {
        return (addressId != null) ? AddressDto.of(addressService.getById(addressId)) : null;
    }
}
