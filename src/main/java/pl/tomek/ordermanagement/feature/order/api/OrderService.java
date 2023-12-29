package pl.tomek.ordermanagement.feature.order.api;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface OrderService {
    OrderDto create(@Valid OrderCreate orderCreate);

    void delete(UUID id);

    OrderDto getById(UUID id);

    Set<OrderDto> getAll();

    Set<OrderDto> get(LocalDate startDate, LocalDate endDate);
}
