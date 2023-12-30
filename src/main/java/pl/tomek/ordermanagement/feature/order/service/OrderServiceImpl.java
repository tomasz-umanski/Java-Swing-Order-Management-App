package pl.tomek.ordermanagement.feature.order.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.order.api.Order;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;
import pl.tomek.ordermanagement.feature.order.api.OrderService;
import pl.tomek.ordermanagement.feature.order.exception.OrderCreateValidatorException;
import pl.tomek.ordermanagement.feature.validation.ObjectsValidator;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ObjectsValidator<OrderCreate> validator;

    @Autowired
    OrderServiceImpl(OrderRepository orderRepository, ObjectsValidator<OrderCreate> validator) {
        this.orderRepository = orderRepository;
        this.validator = validator;
    }

    @Override
    public Order create(OrderCreate orderCreate) {
        Set<String> violations = validator.validate(orderCreate);
        if (!violations.isEmpty()) {
            throw new OrderCreateValidatorException(violations);
        }
        OrderEntity orderEntity = OrderEntity.of(orderCreate);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return savedOrderEntity.toDomain();
    }

    @Override
    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Order getById(UUID id) {
        return orderRepository.getReferenceById(id).toDomain();
    }

    @Override
    public Set<Order> getAll() {
        return orderRepository.findAll().stream().map(OrderEntity::toDomain).collect(Collectors.toSet());
    }

    @Override
    public Set<Order> get(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Order> get(UUID customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toSet());
    }
}
