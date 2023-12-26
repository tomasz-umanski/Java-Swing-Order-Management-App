package pl.tomek.ordermanagement.model;

import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.customer.api.Customer;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Order {
    UUID id;
    Date orderDate;
    Set<OrderItem> orderItems;
    Customer customer;
    Address shippingAddress;
}
