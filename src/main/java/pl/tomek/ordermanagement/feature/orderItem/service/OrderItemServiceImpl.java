package pl.tomek.ordermanagement.feature.orderItem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemCreate;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.feature.orderItem.exception.OrderItemCreateValidatorException;
import pl.tomek.ordermanagement.feature.validation.ObjectsValidator;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ObjectsValidator<OrderItemCreate> validator;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ObjectsValidator<OrderItemCreate> validator) {
        this.orderItemRepository = orderItemRepository;
        this.validator = validator;
    }

    @Override
    public OrderItem create(OrderItemCreate orderItemCreate) {
        Set<String> violations = validator.validate(orderItemCreate);
        if (!violations.isEmpty()) {
            throw new OrderItemCreateValidatorException(violations);
        }
        OrderItemEntity orderItemEntity = OrderItemEntity.of(orderItemCreate);
        OrderItemEntity savedOrderItemEntity = orderItemRepository.save(orderItemEntity);
        return savedOrderItemEntity.toDomain();
    }

    @Override
    public void delete(UUID id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderItem getById(UUID id) {
        return orderItemRepository.getReferenceById(id).toDomain();
    }

    @Override
    public Set<OrderItem> getAll() {
        return orderItemRepository.findAll().stream().map(OrderItemEntity::toDomain).collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItem> getByOrderId(UUID orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItem> getByProductId(UUID productId) {
        return orderItemRepository.findByProductId(productId).stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toSet());
    }
}
