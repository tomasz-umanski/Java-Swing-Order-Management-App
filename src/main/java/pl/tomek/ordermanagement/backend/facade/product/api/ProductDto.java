package pl.tomek.ordermanagement.backend.facade.product.api;

import pl.tomek.ordermanagement.backend.feature.product.api.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(UUID id,
                         String name,
                         String description,
                         String SKU,
                         BigDecimal estimatedNetUnitPrice,
                         BigDecimal estimatedGrossUnitPrice,
                         BigDecimal length,
                         BigDecimal height,
                         BigDecimal width,
                         BigDecimal weight) {

    @Override
    public String toString() {
        return name;
    }

    public static ProductDto of(Product product) {
        return new ProductDto(
                product.id(),
                product.name(),
                product.description(),
                product.SKU(),
                product.estimatedNetUnitPrice(),
                product.estimatedGrossUnitPrice(),
                product.length(),
                product.height(),
                product.width(),
                product.weight()
        );
    }
}
