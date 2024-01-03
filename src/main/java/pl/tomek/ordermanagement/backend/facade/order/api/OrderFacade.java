package pl.tomek.ordermanagement.backend.facade.order.api;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface OrderFacade {
    OrderDto saveOrder(OrderDto orderDto);

    void deleteOrder(OrderDto orderDto);

    Set<OrderDto> getAllOrders();

    Set<OrderDto> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);

    Set<OrderDto> getOrdersByCustomer(UUID customerId);
}
