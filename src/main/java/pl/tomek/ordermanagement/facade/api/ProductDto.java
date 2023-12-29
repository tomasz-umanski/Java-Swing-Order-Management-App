package pl.tomek.ordermanagement.facade.api;

import java.math.BigDecimal;

public record ProductDto(String name,
                         String description,
                         String SKU,
                         BigDecimal estimatedNetUnitPrice,
                         BigDecimal estimatedGrossUnitPrice,
                         BigDecimal length,
                         BigDecimal height,
                         BigDecimal width,
                         BigDecimal weight) {
}
