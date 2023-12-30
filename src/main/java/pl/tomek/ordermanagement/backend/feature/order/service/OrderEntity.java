package pl.tomek.ordermanagement.backend.feature.order.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import pl.tomek.ordermanagement.backend.feature.order.api.Order;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderCreate;

import java.time.LocalDate;
import java.util.UUID;

import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "t_order")
@SQLDelete(sql = "UPDATE t_order SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
class OrderEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private UUID customerId;
    @NotNull
    private UUID shippingAddressId;
    private boolean deleted = FALSE;

    public static OrderEntity of(OrderCreate orderCreate) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.orderDate = orderCreate.orderDate();
        orderEntity.customerId = orderCreate.customerId();
        orderEntity.shippingAddressId = orderCreate.shippingAddressId();
        return orderEntity;
    }

    public Order toDomain() {
        return new Order(
                this.id,
                this.orderDate,
                this.customerId,
                this.shippingAddressId
        );
    }
}