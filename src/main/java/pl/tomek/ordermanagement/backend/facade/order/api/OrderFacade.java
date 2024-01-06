package pl.tomek.ordermanagement.backend.facade.order.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderFacade {
    OrderDto saveOrder(OrderCreateDto orderCreateDto);

    void deleteOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    List<OrderDto> getFilteredOrders(LocalDate startDate,
                                     LocalDate endDate,
                                     BigDecimal fromValue,
                                     BigDecimal toValue,
                                     UUID customerId);

}
