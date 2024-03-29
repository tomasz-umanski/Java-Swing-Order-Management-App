package pl.tomek.ordermanagement.backend.feature.product.api;

import jakarta.validation.Valid;

import java.util.Set;
import java.util.UUID;

public interface ProductService {
    Product create(@Valid ProductCreate productCreate);

    void delete(UUID id);

    Product getById(UUID id);

    Set<Product> getAll();

    Set<Product> get(String namePattern);
}
