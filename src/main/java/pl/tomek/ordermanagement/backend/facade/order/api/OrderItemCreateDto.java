package pl.tomek.ordermanagement.backend.facade.order.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemCreate;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemCreateDto(@Valid
                                 @NotNull(message = "Product is mandatory")
                                 ProductDto product,
                                 @NotNull(message = "Quantity is mandatory")
                                 BigDecimal quantity,
                                 BigDecimal discount,
                                 @NotNull(message = "Net Price is mandatory")
                                 BigDecimal netPrice,
                                 @NotNull(message = "Gross Price is mandatory")
                                 BigDecimal grossPrice) {

    public OrderItemCreate toDomainCreate(UUID orderId) {
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