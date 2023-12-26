package pl.tomek.ordermanagement.feature.address;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AddressServiceTest {

    @Autowired
    private AddressService service;

    private static final AddressCreate addressCreate = new AddressCreate(
            "a",
            "b",
            "c", "Kielce",
            "25-666",
            "Świętokrzyskie",
            "Poland"
    );

    @Test
    void shouldSaveAssetAndAssignAnId() {
        Address createdAddress = service.create(addressCreate);
        assertNotNull(createdAddress.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        Address createdAddress = service.create(addressCreate);
        assertNotNull(createdAddress.id());

        Address retrievedAddress = service.getById(createdAddress.id());
        assertNotNull(retrievedAddress);
        assertEquals(retrievedAddress.city(), addressCreate.city());
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        Address createdAddress = service.create(addressCreate);
        assertNotNull(createdAddress.id());

        service.delete(createdAddress.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdAddress.id()));
    }


}