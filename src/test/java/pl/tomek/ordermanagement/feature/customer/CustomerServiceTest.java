package pl.tomek.ordermanagement.feature.customer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.feature.customer.api.Customer;
import pl.tomek.ordermanagement.feature.customer.api.CustomerCreate;
import pl.tomek.ordermanagement.feature.customer.api.CustomerService;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    private static final CustomerCreate customerCreate = new CustomerCreate(
            "Jan",
            "Testowy",
            "Firma",
            "1234563218",
            UUID.randomUUID(),
            UUID.randomUUID()
    );

    @Test
    void shouldSaveAssetAndAssignAnId() {
        Customer createdCustomer = service.create(customerCreate);
        assertNotNull(createdCustomer.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        Customer createdCustomer = service.create(customerCreate);
        assertNotNull(createdCustomer.id());

        Customer retrievedCustomer = service.getById(createdCustomer.id());
        assertNotNull(retrievedCustomer);
        assertEquals(retrievedCustomer.companyName(), customerCreate.companyName());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        service.create(customerCreate);
        service.create(customerCreate);

        Set<Customer> retrievedCustomerSet = service.getAll();

        assertNotNull(retrievedCustomerSet);
        assertEquals(retrievedCustomerSet.size(), 2);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        Customer createdCustomer = service.create(customerCreate);
        assertNotNull(createdCustomer.id());

        service.delete(createdCustomer.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdCustomer.id()));
    }
}