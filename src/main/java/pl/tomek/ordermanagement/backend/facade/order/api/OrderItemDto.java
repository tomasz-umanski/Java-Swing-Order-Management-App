package pl.tomek.ordermanagement.backend.facade.order.api;

import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemCreate;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(UUID id,
                           ProductDto product,
                           BigDecimal quantity,
                           BigDecimal discount,
                           BigDecimal netPrice,
                           BigDecimal grossPrice) {

    public static OrderItemDto of(OrderItem orderItem, ProductDto productDto) {
        return new OrderItemDto(
                orderItem.id(),
                productDto,
                orderItem.quantity(),
                orderItem.discount(),
                orderItem.netPrice(),
                orderItem.grossPrice()
        );
    }

    public OrderItemCreate toCreate(UUID orderId) {
        return new OrderItemCreate(
                orderId,
                this.product().id(),
                this.quantity,
                this.discount,
                this.netPrice,
                this.grossPrice
        );
    }
}
