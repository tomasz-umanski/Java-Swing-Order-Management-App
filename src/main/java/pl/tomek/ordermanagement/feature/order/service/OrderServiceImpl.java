package pl.tomek.ordermanagement.feature.order.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.order.api.Order;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;
import pl.tomek.ordermanagement.feature.order.api.OrderService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(OrderCreate orderCreate) {
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
}
