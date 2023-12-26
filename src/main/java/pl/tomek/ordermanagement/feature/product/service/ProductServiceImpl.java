package pl.tomek.ordermanagement.feature.product.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.product.api.Product;
import pl.tomek.ordermanagement.feature.product.api.ProductCreate;
import pl.tomek.ordermanagement.feature.product.api.ProductService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(ProductCreate productCreate) {
        ProductEntity productEntity = ProductEntity.of(productCreate);
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        return savedProductEntity.toDomain();
    }

    @Override
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product getById(UUID id) {
        return productRepository.getReferenceById(id).toDomain();
    }

    @Override
    @Transactional
    public Set<Product> getAll() {
        return productRepository.findAll().stream().map(ProductEntity::toDomain).collect(Collectors.toSet());
    }
}
