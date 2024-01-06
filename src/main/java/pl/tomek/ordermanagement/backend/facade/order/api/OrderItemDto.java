package pl.tomek.ordermanagement.backend.facade.order.api;

import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(UUID id,
                           ProductDto product,
                           BigDecimal quantity,
                           BigDecimal discount,
                           BigDecimal netPrice,
                           BigDecimal grossPrice) {

    @Override
    public String toString() {
        return "Product=" + product.name() +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", netPrice=" + netPrice +
                ", grossPrice=" + grossPrice;
    }

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

    public OrderItemCreateDto toCreateDto() {
        return new OrderItemCreateDto(
                this.product,
                this.quantity(),
                this.discount(),
                this.netPrice(),
                this.grossPrice()
        );
    }
}
