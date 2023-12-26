package pl.tomek.ordermanagement.feature.product.api;

import java.math.BigDecimal;

public record ProductCreate(String name,
                            String description,
                            String SKU,
                            BigDecimal estimatedNetUnitPrice,
                            BigDecimal estimatedGrossUnitPrice,
                            BigDecimal length,
                            BigDecimal height,
                            BigDecimal width,
                            BigDecimal weight) {
}
