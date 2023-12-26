package pl.tomek.ordermanagement.feature.orderItem.api;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemCreate(
        UUID orderId,
        BigDecimal quantity,
        BigDecimal discount,
        BigDecimal netPrice,
        BigDecimal grossPrice) {
}
