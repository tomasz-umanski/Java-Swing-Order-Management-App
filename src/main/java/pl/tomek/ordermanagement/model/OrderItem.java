package pl.tomek.ordermanagement.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    UUID id;
    BigDecimal quantity;
    BigDecimal discount;
    BigDecimal netPrice;
    BigDecimal grossPrice;
}