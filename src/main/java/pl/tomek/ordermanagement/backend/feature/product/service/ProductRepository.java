package pl.tomek.ordermanagement.backend.feature.product.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Set<ProductEntity> findByNameContainingIgnoreCase(String namePattern);
}