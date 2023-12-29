package pl.tomek.ordermanagement.feature.customer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.annotation.UnitTest;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;
import pl.tomek.ordermanagement.feature.customer.exception.CustomerCreateValidatorException;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Test
    void shouldSaveAssetAndAssignAnId() {
        CustomerCreate customerCreate = mockCustomerCreate();
        Customer createdCustomer = service.create(customerCreate);
        assertNotNull(createdCustomer.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        CustomerCreate customerCreate = mockCustomerCreate();
        Customer createdCustomer = service.create(customerCreate);
        assertNotNull(createdCustomer.id());

        Customer retrievedCustomer = service.getById(createdCustomer.id());
        assertNotNull(retrievedCustomer);
        assertEquals(retrievedCustomer.companyName(), customerCreate.companyName());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        CustomerCreate customerCreate = mockCustomerCreate();
        CustomerCreate secondCustomerCreate = mockCustomerCreate();
        service.create(customerCreate);
        service.create(secondCustomerCreate);

        Set<Customer> retrievedCustomerSet = service.getAll();

        assertNotNull(retrievedCustomerSet);
        assertEquals(retrievedCustomerSet.size(), 2);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        CustomerCreate customerCreate = mockCustomerCreate();
        Customer createdCustomer = service.create(customerCreate);
        assertNotNull(createdCustomer.id());

        service.delete(createdCustomer.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdCustomer.id()));
    }

    @Test
    void shouldThrowValidatorException() {
        CustomerCreate customerCreate = mockCustomerCreateWithoutName();
        assertThrows(CustomerCreateValidatorException.class, () -> service.create(customerCreate));
    }

    private CustomerCreate mockCustomerCreate() {
        return new CustomerCreate(
                "Jan",
                "Testowy",
                "Firma",
                "1234563218",
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }

    private CustomerCreate mockCustomerCreateWithoutName() {
        return new CustomerCreate(
                null,
                "Testowy",
                "Firma",
                "1234563218",
                UUID.randomUUID(),
                UUID.randomUUID()
        );
    }
}