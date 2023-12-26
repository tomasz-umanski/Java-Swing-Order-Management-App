package pl.tomek.ordermanagement.feature.address;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.BaseTest;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceTest extends BaseTest {

    @Autowired
    private AddressService addressService;

    private final AddressCreate addressCreate = new AddressCreate(
            "a",
            "b",
            "c", "Kielce",
            "25-666",
            "Świętokrzskie",
            "Poland"
    );


    @Test
    void shouldSaveAssetAndAssignAnId() {
        Address createdAddress = addressService.create(addressCreate);
        assertNotNull(createdAddress.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        Address createdAddress = addressService.create(addressCreate);
        assertNotNull(createdAddress.id());

        Address retrievedAddress = addressService.getById(createdAddress.id());
        assertNotNull(retrievedAddress);
        assertEquals(retrievedAddress.city(), addressCreate.city());
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        Address createdAddress = addressService.create(addressCreate);
        assertNotNull(createdAddress.id());

        addressService.delete(createdAddress.id());

        assertThrows(EntityNotFoundException.class, () -> addressService.getById(createdAddress.id()));
    }


}