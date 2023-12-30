package pl.tomek.ordermanagement.facade.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.facade.order.api.OrderDto;
import pl.tomek.ordermanagement.facade.order.api.OrderFacadeService;
import pl.tomek.ordermanagement.facade.order.api.OrderItemDto;
import pl.tomek.ordermanagement.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.feature.order.api.Order;
import pl.tomek.ordermanagement.feature.order.api.OrderService;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.feature.product.api.ProductService;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class OrderFacadeServiceImpl implements OrderFacadeService {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final AddressService addressService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public OrderFacadeServiceImpl(OrderService orderService, OrderItemService orderItemService, AddressService addressService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.addressService = addressService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        AddressDto addressDto = createAddress(orderDto.shippingAddress());
        Order order = orderService.create(orderDto.toCreate(addressDto.id()));
        Set<OrderItemDto> orderItemsDto = createOrderItems(orderDto.orderItems(), order.id());
        return OrderDto.of(order, addressDto, orderItemsDto, orderDto.customer());
    }

    @Override
    public void deleteOrder(OrderDto orderDto) {
        deleteOrderItemSet(orderDto.orderItems());
        orderService.delete(orderDto.id());
    }

    @Override
    public Set<OrderDto> getAllOrders() {
        return orderService.getAll().stream()
                .map(this::getOrderDtoDetails)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderService.get(startDate, endDate).stream()
                .map(this::getOrderDtoDetails)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> getOrdersByCustomer(UUID customerId) {
        return orderService.get(customerId).stream()
                .map(this::getOrderDtoDetails)
                .collect(Collectors.toSet());
    }

    private AddressDto createAddress(AddressDto addressDto) {
        if (addressDto.id() != null) {
            return addressDto;
        }
        AddressCreate addressCreate = addressDto.toCreate();
        return AddressDto.of(addressService.create(addressCreate));
    }

    private Set<OrderItemDto> createOrderItems(Set<OrderItemDto> orderItemsDto, UUID orderId) {
        return orderItemsDto.stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = orderItemService.create(orderItemDto.toCreate(orderId));
                    return OrderItemDto.of(orderItem, orderItemDto.product());
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
        return CustomerDto.of(customer, homeAddressDto, shippingAddressDto);
    }

    private AddressDto getAddressById(UUID addressId) {
        return (addressId != null) ? AddressDto.of(addressService.getById(addressId)) : null;
    }

    private ProductDto getProductById(UUID productId) {
        return ProductDto.of(productService.getById(productId));
    }
}
