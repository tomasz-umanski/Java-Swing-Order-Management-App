package pl.tomek.ordermanagement.backend.facade.product.api;

import java.util.List;
import java.util.UUID;

public interface ProductFacade {
    ProductDto saveProduct(ProductCreateDto productCreateDto);

    void deleteProduct(UUID id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsContainingNamePattern(String nameLike);
}