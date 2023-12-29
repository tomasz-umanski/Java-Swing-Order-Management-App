package pl.tomek.ordermanagement.feature.orderItem.api;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemCreate(@NotNull(message = "OrderId is mandatory")
                              UUID orderId,
                              @NotNull(message = "ProductId is mandatory")
                              UUID productId,
                              @NotNull(message = "Quantity is mandatory")
                              BigDecimal quantity,
                              BigDecimal discount,
                              @NotNull(message = "NetPrice is mandatory")
                              BigDecimal netPrice,
                              @NotNull(message = "GrossPrice is mandatory")
                              BigDecimal grossPrice) {
}
