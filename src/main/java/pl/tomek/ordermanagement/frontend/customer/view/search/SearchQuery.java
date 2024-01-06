package pl.tomek.ordermanagement.frontend.customer.view.search;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SearchQuery(LocalDate startDate,
                          LocalDate endDate,
                          BigDecimal fromValue) {
}
