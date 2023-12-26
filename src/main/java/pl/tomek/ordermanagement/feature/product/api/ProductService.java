package pl.tomek.ordermanagement.feature.product.api;

import java.util.Set;
import java.util.UUID;

public interface ProductService {
    Product create(ProductCreate productCreate);

    void delete(UUID id);

    Product getById(UUID id);

    Set<Product> getAll();
}
