package pl.tomek.ordermanagement.facade.product.api;

import pl.tomek.ordermanagement.feature.product.api.Product;

import java.util.Set;
import java.util.UUID;

public interface ProductFacadeService {
    ProductDto saveProduct(ProductDto productDto);

    void deleteProduct(UUID id);

    Set<Product> getAllProducts();

    Set<Product> getProductsContainingNamePattern(String nameLike);
}