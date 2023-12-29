package pl.tomek.ordermanagement.feature.order.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Repository
interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Set<OrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}