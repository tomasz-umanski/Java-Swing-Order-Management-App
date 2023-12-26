package pl.tomek.ordermanagement.feature.product.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}