package pl.tomek.ordermanagement.feature.customer.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;

import java.util.UUID;

@Entity
@Table(name = "customer")
class CustomerEntity {
    @Id
    final UUID id = UUID.randomUUID();
    private String name;
    private String lastName;
    private String companyName;
    private String taxIdNumber;
    private UUID homeAddressId;
    private UUID shippingAddressId;

    public static CustomerEntity of(CustomerCreate customerCreate) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.name = customerCreate.name();
        customerEntity.lastName = customerCreate.lastName();
        customerEntity.companyName = customerCreate.companyName();
        customerEntity.taxIdNumber = customerCreate.taxIdNumber();
        customerEntity.homeAddressId = customerCreate.homeAddressId();
        customerEntity.shippingAddressId = customerCreate.shippingAddressId();
        return customerEntity;
    }

    public Customer toDomain() {
        return new Customer(
                this.id,
                this.name,
                this.lastName,
                this.companyName,
                this.taxIdNumber,
                this.homeAddressId,
                this.shippingAddressId
        );
    }
}
