package pl.tomek.ordermanagement.feature.order.api;

import jakarta.validation.Valid;

import java.util.Set;
import java.util.UUID;

public interface OrderService {
    Order create(@Valid OrderCreate orderCreate);

    void delete(UUID id);

    Order getById(UUID id);

    Set<Order> getAll();
}
