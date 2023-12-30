package pl.tomek.ordermanagement.backend.feature.product.api;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id,
                      String name,
                      String description,
                      String SKU,
                      BigDecimal estimatedNetUnitPrice,
                      BigDecimal estimatedGrossUnitPrice,
                      BigDecimal length,
                      BigDecimal height,
                      BigDecimal width,
                      BigDecimal weight) {
}
