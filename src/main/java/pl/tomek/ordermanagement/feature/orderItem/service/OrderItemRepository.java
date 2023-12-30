package pl.tomek.ordermanagement.feature.orderItem.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
    Set<OrderItemEntity> findByOrderId(UUID customerId);
    Set<OrderItemEntity> findByProductId(UUID productId);
}
