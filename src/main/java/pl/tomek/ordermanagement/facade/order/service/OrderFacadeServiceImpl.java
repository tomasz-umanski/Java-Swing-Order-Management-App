package pl.tomek.ordermanagement.facade.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.order.api.OrderDto;
import pl.tomek.ordermanagement.facade.order.api.OrderFacadeService;
import pl.tomek.ordermanagement.feature.order.api.OrderService;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
class OrderFacadeServiceImpl implements OrderFacadeService {
    private final OrderService orderService;

    @Autowired
    public OrderFacadeServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public void deleteOrder(OrderDto orderDto) {
    }

    @Override
    public Set<OrderDto> getAllOrders() {
        return null;
    }

    @Override
    public Set<OrderDto> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Set<OrderDto> getOrdersByCustomer(UUID customerId) {
        return null;
    }
}
