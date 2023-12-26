package pl.tomek.ordermanagement.feature.product.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pl.tomek.ordermanagement.feature.product.api.Product;
import pl.tomek.ordermanagement.feature.product.api.ProductCreate;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "t_product")
class ProductEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    private String name;
    private String description;
    @Column(unique = true)
    private String SKU;
    private BigDecimal estimatedNetUnitPrice;
    private BigDecimal estimatedGrossUnitPrice;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal weight;

    public static ProductEntity of(ProductCreate productCreate) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.name = productCreate.name();
        productEntity.description = productCreate.description();
        productEntity.SKU = productCreate.SKU();
        productEntity.estimatedNetUnitPrice = productCreate.estimatedNetUnitPrice();
        productEntity.estimatedGrossUnitPrice = productCreate.estimatedGrossUnitPrice();
        productEntity.length = productCreate.length();
        productEntity.height = productCreate.height();
        productEntity.width = productCreate.width();
        productEntity.weight = productCreate.weight();
        return productEntity;
    }

    public Product toDomain() {
        return new Product(
                this.id,
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
