package pl.tomek.ordermanagement.backend.facade.order.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderFacade {
    OrderDto saveOrder(OrderCreateDto orderCreateDto);

    void deleteOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    List<OrderDto> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);

    List<OrderDto> getOrdersByCustomer(UUID customerId);
}
