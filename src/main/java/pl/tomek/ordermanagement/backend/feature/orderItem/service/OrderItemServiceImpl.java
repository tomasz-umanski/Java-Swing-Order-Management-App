package pl.tomek.ordermanagement.backend.feature.orderItem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemCreate;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.backend.feature.orderItem.exception.OrderItemCreateValidatorException;
import pl.tomek.ordermanagement.backend.validation.ObjectsValidator;

import java.util.List;
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
    public List<OrderItem> getByOrderId(UUID orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Set<OrderItem> getByProductId(UUID productId) {
        return orderItemRepository.findByProductId(productId).stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toSet());
    }
}
