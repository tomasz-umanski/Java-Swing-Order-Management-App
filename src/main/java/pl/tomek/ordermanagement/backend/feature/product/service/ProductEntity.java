package pl.tomek.ordermanagement.backend.feature.product.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import pl.tomek.ordermanagement.backend.feature.product.api.Product;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductCreate;

import java.math.BigDecimal;
import java.util.UUID;

import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "t_product")
@SQLDelete(sql = "UPDATE t_product SET archived = true WHERE id=?")
@SQLRestriction("archived=false")
class ProductEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    @NotNull
    private String name;
    private String description;
    @NotNull
    @Column(unique = true)
    private String SKU;
    @NotNull
    private BigDecimal estimatedNetUnitPrice;
    @NotNull
    private BigDecimal estimatedGrossUnitPrice;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal weight;
    @NotNull
    private final boolean archived = FALSE;

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
