package pl.tomek.ordermanagement.feature.order.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pl.tomek.ordermanagement.feature.order.api.Order;
import pl.tomek.ordermanagement.feature.order.api.OrderCreate;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "t_order")
class OrderEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    private Date orderDate;
    private UUID customerId;
    private UUID shippingAddressId;

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