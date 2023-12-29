package pl.tomek.ordermanagement.feature.orderItem.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemCreate;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "t_order_item")
class OrderItemEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID productId;
    @NotNull
    private BigDecimal quantity;
    private BigDecimal discount;
    @NotNull
    private BigDecimal netPrice;
    @NotNull
    private BigDecimal grossPrice;

    public static OrderItemEntity of(OrderItemCreate orderItemCreate) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.orderId = orderItemCreate.orderId();
        orderItemEntity.productId = orderItemCreate.productId();
        orderItemEntity.quantity = orderItemCreate.quantity();
        orderItemEntity.discount = orderItemCreate.discount();
        orderItemEntity.netPrice = orderItemCreate.netPrice();
        orderItemEntity.grossPrice = orderItemCreate.grossPrice();
        return orderItemEntity;
    }

    public OrderItem toDomain() {
        return new OrderItem(
                this.id,
                this.orderId,
                this.productId,
                this.quantity,
                this.discount,
                this.netPrice,
                this.grossPrice
        );
    }
}
