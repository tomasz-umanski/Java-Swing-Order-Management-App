package pl.tomek.ordermanagement.feature.order.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import pl.tomek.ordermanagement.feature.order.api.OrderDto;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_order")
class OrderEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private UUID customerId;
    @NotNull
    private UUID shippingAddressId;

    public static OrderEntity of(OrderCreate orderCreate) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.orderDate = orderCreate.orderDate();
        orderEntity.customerId = orderCreate.customerId();
        orderEntity.shippingAddressId = orderCreate.shippingAddressId();
        return orderEntity;
    }

    public OrderDto toDomain() {
        return new OrderDto(
                this.id,
                this.orderDate,
                this.customerId,
                this.shippingAddressId
        );
    }
}