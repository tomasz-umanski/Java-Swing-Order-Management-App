package pl.tomek.ordermanagement.backend.facade.customer.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.facade.customer.api.*;
import pl.tomek.ordermanagement.backend.facade.customer.exception.CustomerCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.feature.address.api.Address;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressService;
import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.backend.feature.order.api.Order;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.backend.validation.ObjectsValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
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
        return CustomerDto.of(customerService.create(customerCreate), homeAddress, shippingAddress, null);
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
                    List<CustomerOrderDto> customerOrderDtoList = orderService.get(customer.id())
                            .stream().map(order -> CustomerOrderDto.of(order, getOrderValue(order.id())))
                            .toList();
                    return CustomerDto.of(customer, homeAddress, shippingAddress, customerOrderDtoList);
                })
                .collect(Collectors.toList());
    }

    private BigDecimal getOrderValue(UUID orderId) {
        List<OrderItem> orderItemList = orderItemService.getByOrderId(orderId);
        return orderItemList.stream()
                .map(orderItem -> orderItem.grossPrice().multiply(orderItem.quantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<AddressDto> getCustomersAddresses(UUID customerId) {
        Customer customer = customerService.getById(customerId);
        List<AddressDto> addresses = new ArrayList<>();
        if (customer != null) {
            addAddressIfExists(addresses, customer.homeAddressId());
            addAddressIfExists(addresses, customer.shippingAddressId());
        }
        return addresses;
    }

    @Override
    public List<CustomerDto> getCustomersWithFilteredOrders(LocalDate startDate, LocalDate endDate, BigDecimal minValue) {
        if (startDate == null && endDate == null && minValue == null) {
            return getAllCustomers();
        }

        Map<UUID, List<CustomerOrderDto>> customerOrderMap = new HashMap<>();
        List<Order> orders = orderService.get(startDate, endDate);

        for (Order order : orders) {
            BigDecimal orderValue = getOrderValue(order.id());
            if (minValue == null || minValue.compareTo(orderValue) <= 0) {
                CustomerOrderDto customerOrderDto = CustomerOrderDto.of(order, orderValue);
                customerOrderMap.computeIfAbsent(order.customerId(), k -> new ArrayList<>()).add(customerOrderDto);
            }
        }

        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerOrderMap.keySet().forEach(customerId -> {
            Customer customer = customerService.getById(customerId);
            if (customer != null) {
                CustomerDto customerDto = CustomerDto.of(
                        customer,
                        getAddressById(customer.homeAddressId()),
                        getAddressById(customer.shippingAddressId()),
                        customerOrderMap.get(customerId)
                );
                customerDtoList.add(customerDto);
            }
        });

        return customerDtoList;
    }


    private void addAddressIfExists(List<AddressDto> addresses, UUID addressId) {
        if (addressId != null) {
            Address address = addressService.getById(addressId);
            if (address != null) {
                addresses.add(AddressDto.of(address));
            }
        }
    }

    private AddressDto getAddressById(UUID addressId) {
        return (addressId != null) ? AddressDto.of(addressService.getById(addressId)) : null;
    }
}
