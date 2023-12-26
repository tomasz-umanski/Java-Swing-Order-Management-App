package pl.tomek.ordermanagement.feature.customer.service;

import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.database.api.BaseEntity;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;

import java.util.UUID;

class CustomerEntity extends BaseEntity {
    String name;
    String lastName;
    String companyName;
    String taxIdNumber;
    UUID homeAddressId;
    UUID shippingAddressId;

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

    private CustomerEntity() {
    }
}
