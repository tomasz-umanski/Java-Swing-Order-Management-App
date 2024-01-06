package pl.tomek.ordermanagement.backend.feature.order.api;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderService {
    Order create(@Valid OrderCreate orderCreate);

    void delete(UUID id);

    Order getById(UUID id);

    Set<Order> getAll();

    List<Order> get(LocalDate startDate, LocalDate endDate);

    Set<Order> get(UUID customerId);
}
