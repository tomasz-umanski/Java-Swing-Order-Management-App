package pl.tomek.ordermanagement.feature.orderItem.api;

import java.util.Set;
import java.util.UUID;

public interface OrderItemService {
    OrderItem create(OrderItemCreate orderItemCreate);

    void delete(UUID id);

    OrderItem getById(UUID id);

    Set<OrderItem> getAll();
}
