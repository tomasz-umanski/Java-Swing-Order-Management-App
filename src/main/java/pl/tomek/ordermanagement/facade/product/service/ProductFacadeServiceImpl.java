package pl.tomek.ordermanagement.facade.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.facade.product.api.ProductFacadeService;
import pl.tomek.ordermanagement.feature.product.api.Product;
import pl.tomek.ordermanagement.feature.product.api.ProductService;

import java.util.Set;
import java.util.UUID;

@Service
class ProductFacadeServiceImpl implements ProductFacadeService {
    private final ProductService productService;

    @Autowired
    public ProductFacadeServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        return ProductDto.of(productService.create(productDto.toCreate()));
    }

    @Override
    public void deleteProduct(UUID id) {
        productService.delete(id);
    }

    @Override
    public Set<Product> getAllProducts() {
        return productService.getAll();
    }

    @Override
    public Set<Product> getProductsContainingNamePattern(String namePattern) {
        return productService.get(namePattern);
    }
}
