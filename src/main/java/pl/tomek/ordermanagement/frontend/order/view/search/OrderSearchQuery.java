package pl.tomek.ordermanagement.frontend.order.view.search;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record OrderSearchQuery(LocalDate startDate,
                               LocalDate endDate,
                               BigDecimal fromValue,
                               BigDecimal toValue,
                               UUID customerId) {
}
