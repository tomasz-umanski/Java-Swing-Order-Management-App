package pl.tomek.ordermanagement.feature.orderItem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemCreate;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class OrderItemServiceImpl implements OrderItemService {

    OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem create(OrderItemCreate orderItemCreate) {
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
}
