package pl.tomek.ordermanagement.backend.facade.customer.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacade;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressService;
import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;

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

    @Autowired
    public CustomerFacadeImpl(AddressService addressService,
                              CustomerService customerService,
                              OrderService orderService, OrderItemService orderItemService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    @Transactional
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        AddressDto homeAddress = createAddress(customerDto.homeAddress());
        AddressDto shippingAddress = !customerDto.homeAddress().equals(customerDto.shippingAddress())
                ? createAddress(customerDto.shippingAddress())
                : homeAddress;
        return createCustomer(customerDto, homeAddress, shippingAddress);
    }

    private AddressDto createAddress(AddressDto addressDto) {
        if (addressDto != null) {
            AddressCreate addressCreate = addressDto.toCreate();
            return AddressDto.of(addressService.create(addressCreate));
        }
        return null;
    }

    private CustomerDto createCustomer(CustomerDto customerDto, AddressDto homeAddress, AddressDto shippingAddress) {
        UUID shippingAddressId = (shippingAddress != null) ? shippingAddress.id() : null;
        CustomerCreate customerCreate = customerDto.toCreate(homeAddress.id(), shippingAddressId);
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
