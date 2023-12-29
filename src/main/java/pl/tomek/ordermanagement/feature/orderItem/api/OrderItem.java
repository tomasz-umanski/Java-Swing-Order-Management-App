package pl.tomek.ordermanagement.feature.orderItem.api;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID id,
                        UUID orderId,
                        UUID productId,
                        BigDecimal quantity,
                        BigDecimal discount,
                        BigDecimal netPrice,
                        BigDecimal grossPrice) {
}
