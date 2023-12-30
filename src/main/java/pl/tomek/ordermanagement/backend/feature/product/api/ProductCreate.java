package pl.tomek.ordermanagement.backend.feature.product.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreate(@NotBlank(message = "Name is mandatory")
                            String name,
                            String description,
                            @NotBlank(message = "SKU is mandatory")
                            String SKU,
                            @NotNull(message = "EstimatedNetUnitPrice is mandatory")
                            BigDecimal estimatedNetUnitPrice,
                            @NotNull(message = "EstimatedGrossUnitPrice is mandatory")
                            BigDecimal estimatedGrossUnitPrice,
                            BigDecimal length,
                            BigDecimal height,
                            BigDecimal width,
                            BigDecimal weight) {
}
