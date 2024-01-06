package pl.tomek.ordermanagement.backend.facade.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.order.api.*;
import pl.tomek.ordermanagement.backend.facade.order.exception.OrderCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressService;
import pl.tomek.ordermanagement.backend.feature.customer.api.Customer;
import pl.tomek.ordermanagement.backend.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.backend.feature.order.api.Order;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductService;
import pl.tomek.ordermanagement.backend.validation.ObjectsValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class OrderFacadeImpl implements OrderFacade {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final AddressService addressService;
    private final ObjectsValidator<OrderCreateDto> validator;

    @Autowired
    public OrderFacadeImpl(OrderService orderService,
                           OrderItemService orderItemService,
                           ProductService productService,
                           CustomerService customerService, AddressService addressService, ObjectsValidator<OrderCreateDto> validator) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.customerService = customerService;
        this.addressService = addressService;
        this.validator = validator;
    }

    @Override
    public OrderDto saveOrder(OrderCreateDto orderCreateDto) {
        Set<String> violations = validator.validate(orderCreateDto);
        if (!violations.isEmpty()) {
            throw new OrderCreateDtoValidatorException(violations);
        }
        Order order = orderService.create(orderCreateDto.toDomainCreate());
        Set<OrderItemDto> orderItemsDto = createOrderItems(orderCreateDto.orderItems(), order.id());
        return OrderDto.of(order, orderCreateDto.shippingAddress(), orderItemsDto, orderCreateDto.customer());
    }

    @Override
    public void deleteOrder(OrderDto orderDto) {
        deleteOrderItemSet(orderDto.orderItems());
        orderService.delete(orderDto.id());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderService.getAll().stream()
                .map(this::getOrderDtoDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderService.get(startDate, endDate).stream()
                .map(this::getOrderDtoDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByCustomer(UUID customerId) {
        return orderService.get(customerId).stream()
                .map(this::getOrderDtoDetails)
                .collect(Collectors.toList());
    }

    private Set<OrderItemDto> createOrderItems(List<OrderItemCreateDto> orderItemsCreateDto, UUID orderId) {
        return orderItemsCreateDto.stream()
                .map(orderItemCreateDto -> {
                    OrderItem orderItem = orderItemService.create(orderItemCreateDto.toDomainCreate(orderId));
                    return OrderItemDto.of(orderItem, orderItemCreateDto.product());
                })
                .collect(Collectors.toSet());
    }

    private void deleteOrderItemSet(Set<OrderItemDto> orderItemsDto) {
        orderItemsDto.forEach(orderItemDto -> orderItemService.delete(orderItemDto.id()));
    }

    private OrderDto getOrderDtoDetails(Order order) {
        AddressDto addressDto = getAddressById(order.shippingAddressId());
        Set<OrderItemDto> orderItemsDto = getOrderItemDtoSetByOrderId(order.id());
        CustomerDto customerDto = getCustomerById(order.customerId());
        return OrderDto.of(order, addressDto, orderItemsDto, customerDto);
    }

    private Set<OrderItemDto> getOrderItemDtoSetByOrderId(UUID orderId) {
        return orderItemService.getByOrderId(orderId).stream().map(orderItem -> {
            ProductDto productDto = getProductById(orderItem.productId());
            return OrderItemDto.of(orderItem, productDto);
        }).collect(Collectors.toSet());
    }

    private CustomerDto getCustomerById(UUID customerId) {
        Customer customer = customerService.getById(customerId);
        AddressDto homeAddressDto = getAddressById(customer.homeAddressId());
        AddressDto shippingAddressDto = getAddressById(customer.shippingAddressId());
        return CustomerDto.of(customer, homeAddressDto, shippingAddressDto, null);
    }

    private AddressDto getAddressById(UUID addressId) {
        return (addressId != null) ? AddressDto.of(addressService.getById(addressId)) : null;
    }

    private ProductDto getProductById(UUID productId) {
        return ProductDto.of(productService.getById(productId));
    }
}
