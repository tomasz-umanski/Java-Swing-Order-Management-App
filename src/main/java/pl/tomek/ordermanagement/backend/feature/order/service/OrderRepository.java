package pl.tomek.ordermanagement.backend.feature.order.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    List<OrderEntity> findByOrderDateLessThanEqual(LocalDate endDate);

    List<OrderEntity> findByOrderDateGreaterThanEqual(LocalDate startDate);

    Set<OrderEntity> findByCustomerId(UUID customerId);
}