package pl.tomek.ordermanagement.facade;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.facade.api.AddressDto;
import pl.tomek.ordermanagement.facade.api.CustomerDto;
import pl.tomek.ordermanagement.facade.api.FacadeService;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;
import pl.tomek.ordermanagement.feature.address.api.AddressService;
import pl.tomek.ordermanagement.feature.address.exception.AddressCreateValidatorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FacadeServiceTest {

    @Autowired
    private FacadeService service;

    @Test
    void shouldSaveCustomer() {
        CustomerDto customerDto = mockCustomerDto();
        CustomerDto createdCustomerDto = service.create(customerDto);
        assertNotNull(createdCustomerDto);
        assertEquals(customerDto.name(), createdCustomerDto.name());
    }

    @Test
    void shouldSaveCustomerWithoutShippingAddress() {
        CustomerDto customerDto = mockCustomerDtoWithoutShippingAddress();
        CustomerDto createdCustomerDto = service.create(customerDto);
        assertNotNull(createdCustomerDto);
        assertEquals(customerDto.name(), createdCustomerDto.name());
    }

    private CustomerDto mockCustomerDto() {
        return new CustomerDto(
                "Jan",
                "Testowy",
                "Firma",
                "1234563218",
                mockAddressDto(),
                mockAddressDto()
        );
    }

    private CustomerDto mockCustomerDtoWithoutShippingAddress() {
        return new CustomerDto(
                "Jan",
                "Testowy",
                "Firma",
                "1234563218",
                mockAddressDto(),
                null
        );
    }

    private AddressDto mockAddressDto() {
        return new AddressDto(
                "a",
                "b",
                "c",
                "Kielce",
                "25-666",
                "Świętokrzyskie",
                "Poland"
        );
    }
}