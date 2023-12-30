package pl.tomek.ordermanagement.backend.feature.orderItem.api;

import jakarta.validation.Valid;

import java.util.Set;
import java.util.UUID;

public interface OrderItemService {
    OrderItem create(@Valid OrderItemCreate orderItemCreate);

    void delete(UUID id);

    OrderItem getById(UUID id);

    Set<OrderItem> getAll();

    Set<OrderItem> getByOrderId(UUID orderId);


    Set<OrderItem> getByProductId(UUID productId);
}
