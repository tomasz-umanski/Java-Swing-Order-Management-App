package pl.tomek.ordermanagement.facade.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.facade.customer.api.CustomerFacadeService;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.feature.order.api.OrderService;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemService;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CustomerFacadeServiceImpl implements CustomerFacadeService {
    private final AddressService addressService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Autowired
    public CustomerFacadeServiceImpl(AddressService addressService,
                                     CustomerService customerService,
                                     OrderService orderService, OrderItemService orderItemService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        AddressDto homeAddress = createAddress(customerDto.homeAddress());
        AddressDto shippingAddress = createAddress(customerDto.shippingAddress());
        return createCustomer(customerDto, homeAddress, shippingAddress);
    }

    @Override
    public void deleteCustomer(CustomerDto customerDto) {
        deleteAddress(customerDto.homeAddress().id());
        deleteAddress(customerDto.shippingAddress().id());
        deleteOrdersForCustomer(customerDto.id());
        customerService.delete(customerDto.id());
    }

    @Override
    public Set<CustomerDto> getAllCustomers() {
        return customerService.getAll().stream()
                .map(customer -> {
                    AddressDto homeAddress = getAddressById(customer.homeAddressId());
                    AddressDto shippingAddress = getAddressById(customer.shippingAddressId());
                    return CustomerDto.of(customer, homeAddress, shippingAddress);
                })
                .collect(Collectors.toSet());
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

    private void deleteAddress(UUID addressId) {
        if (addressId != null) {
            addressService.delete(addressId);
        }
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
}
