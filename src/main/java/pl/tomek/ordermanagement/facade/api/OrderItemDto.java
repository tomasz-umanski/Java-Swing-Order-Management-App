package pl.tomek.ordermanagement.facade.api;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(UUID orderId,
                           BigDecimal quantity,
                           BigDecimal discount,
                           BigDecimal netPrice,
                           BigDecimal grossPrice) {
}
