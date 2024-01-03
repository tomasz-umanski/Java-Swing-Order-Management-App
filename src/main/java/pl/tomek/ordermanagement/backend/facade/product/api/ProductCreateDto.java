package pl.tomek.ordermanagement.backend.facade.product.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductCreate;

import java.math.BigDecimal;

public record ProductCreateDto(@NotBlank(message = "Name is mandatory")
                               String name,
                               String description,
                               @NotNull(message = "Sku is mandatory")
                               String SKU,
                               @NotNull(message = "Estimated Net Unit Price is mandatory")
                               BigDecimal estimatedNetUnitPrice,
                               @NotNull(message = "Estimated Gross Unit Price is mandatory")
                               BigDecimal estimatedGrossUnitPrice,
                               BigDecimal length,
                               BigDecimal height,
                               BigDecimal width,
                               BigDecimal weight) {

    public ProductCreate toDomainCreate() {
        return new ProductCreate(
                this.name,
                this.description,
                this.SKU,
                this.estimatedNetUnitPrice,
                this.estimatedGrossUnitPrice,
                this.length,
                this.height,
                this.width,
                this.weight
        );
    }
}