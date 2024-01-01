package pl.tomek.ordermanagement.backend.facade.product.api;

import pl.tomek.ordermanagement.backend.feature.product.api.Product;

import java.util.Set;
import java.util.UUID;

public interface ProductFacade {
    ProductDto saveProduct(ProductDto productDto);

    void deleteProduct(UUID id);

    Set<Product> getAllProducts();

    Set<Product> getProductsContainingNamePattern(String nameLike);
}