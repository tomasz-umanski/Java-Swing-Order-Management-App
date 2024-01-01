package pl.tomek.ordermanagement.backend.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.utils.BaseTest;
import pl.tomek.ordermanagement.utils.UnitTest;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacadeService;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductFacadeService;
import pl.tomek.ordermanagement.backend.feature.customer.exception.CustomerCreateValidatorException;
import pl.tomek.ordermanagement.backend.feature.product.exception.ProductCreateValidatorException;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class FacadeServiceTest extends BaseTest {

    @Autowired
    private CustomerFacadeService customerService;
    @Autowired
    private ProductFacadeService productService;

    @Test
    void shouldSaveProduct() {
        ProductDto productDto = mockProductDto();
        ProductDto createdProductDto = productService.saveProduct(productDto);
        assertNotNull(createdProductDto.id());
        assertEquals(productDto.name(), createdProductDto.name());
    }

    @Test
    void shouldSaveAndDeleteProduct() {
        ProductDto productDto = mockProductDto();
        ProductDto createdProductDto = productService.saveProduct(productDto);
        assertNotNull(createdProductDto.id());
        productService.deleteProduct(createdProductDto.id());
    }

    @Test
    void shouldThrowValidatorExceptionOnSaveProduct() {
        ProductDto productDto = mockProductDtoWithoutName();
        assertThrows(ProductCreateValidatorException.class, () -> productService.saveProduct(productDto));
    }

    @Test
    void shouldSaveCustomer() {
        CustomerDto customerDto = mockCustomerDto();
        CustomerDto createdCustomerDto = customerService.saveCustomer(customerDto);
        assertNotNull(createdCustomerDto.id());
        assertNotNull(createdCustomerDto.homeAddress().id());
        assertNotNull(createdCustomerDto.shippingAddress().id());
        assertEquals(customerDto.name(), createdCustomerDto.name());
    }

    @Test
    void shouldSaveCustomerWithoutShippingAddress() {
        CustomerDto customerDto = mockCustomerDtoWithoutShippingAddress();
        CustomerDto createdCustomerDto = customerService.saveCustomer(customerDto);
        assertNotNull(createdCustomerDto.id());
        assertNotNull(createdCustomerDto.homeAddress().id());
        assertNull(createdCustomerDto.shippingAddress());
        assertEquals(customerDto.name(), createdCustomerDto.name());
    }

    @Test
    void shouldSaveAndDeleteCustomer() {
        CustomerDto customerDto = mockCustomerDto();
        CustomerDto createdCustomerDto = customerService.saveCustomer(customerDto);
        assertNotNull(createdCustomerDto.id());
        customerService.deleteCustomer(createdCustomerDto);
    }

    @Test
    void shouldSaveCustomersAndRetrieveAll() {
        CustomerDto customerDto = mockCustomerDto();
        CustomerDto customerDtoWithoutShippingAddress = mockCustomerDtoWithoutShippingAddress();
        customerService.saveCustomer(customerDto);
        customerService.saveCustomer(customerDtoWithoutShippingAddress);

        Set<CustomerDto> retrievedCustomerSet = customerService.getAllCustomers();

        assertNotNull(retrievedCustomerSet);
        assertEquals(retrievedCustomerSet.size(), 2);
    }

    @Test
    void shouldThrowValidatorExceptionOnSaveCustomer() {
        CustomerDto customerDto = mockCustomerDtoWithoutName();
        assertThrows(CustomerCreateValidatorException.class, () -> customerService.saveCustomer(customerDto));
    }

//    @Test
//    void shouldRetrieveCustomersWithOrdersInDateRange() {
//        LocalDate startDate = LocalDate.of(2023, 1, 1);
//        LocalDate endDate = LocalDate.of(2023, 12, 31);
//        CustomerDto customerDto = mockCustomerDto();
//        CustomerDto customerDtoWithoutShippingAddress = mockCustomerDtoWithoutShippingAddress();
//        customerDto = customerService.saveCustomer(customerDto);
//        customerDtoWithoutShippingAddress = customerService.saveCustomer(customerDtoWithoutShippingAddress);
//
//        OrderDto orderDtoInRange = mockOrderWithCustomerIdAndOrderDate(
//                customerDto,
//                LocalDate.of(2023, 10, 10)
//        );
//        OrderDto orderDtoNotInRange = mockOrderWithCustomerIdAndOrderDate(
//                customerDtoWithoutShippingAddress,
//                LocalDate.of(2022, 10, 10)
//        );
//
//        orderFacadeService.saveOrder(orderDtoInRange);
//        orderFacadeService.saveOrder(orderDtoNotInRange);
//
//        Set<OrderDto> ordersDto = new HashSet<>();
//        ordersDto.add(orderDtoInRange);
//        ordersDto.add(orderDtoNotInRange);
//
//        Set<CustomerDto> retrievedCustomerSet = customerService.getCustomersByOrderDateRange(startDate, endDate);
//
//        assertNotNull(retrievedCustomerSet);
//        assertEquals(retrievedCustomerSet.size(), 1);
//    }
//
//    private OrderDto mockOrderWithCustomerIdAndOrderDate(CustomerDto customerDto, LocalDate orderDate) {
//        return new OrderDto(
//                orderDate,
//                customerDto,
//                customerDto.homeAddress(),
//                mockOrderItemDtoSet()
//        );
//    }
//
//    private Set<OrderItemDto> mockOrderItemDtoSet() {
//        return new HashSet<>();
//    }

    private ProductDto mockProductDto() {
        return new ProductDto(
                null,
                "Notebook",
                "15 inch ultra thin",
                UUID.randomUUID().toString(),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(123),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)
        );
    }

    private ProductDto mockProductDtoWithoutName() {
        return new ProductDto(
                null,
                null,
                "15 inch ultra thin",
                UUID.randomUUID().toString(),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(123),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)
        );
    }

    private CustomerDto mockCustomerDto() {
        return new CustomerDto(
                null,
                "Jan",
                "Testowy",
                "Firma",
                "1234563218",
                mockAddressDto(),
                mockAddressDto()
        );
    }

    private CustomerDto mockCustomerDtoWithoutName() {
        return new CustomerDto(
                null,
                null,
                "Testowy",
                "Firma",
                "1234563218",
                mockAddressDto(),
                mockAddressDto()
        );
    }

    private CustomerDto mockCustomerDtoWithoutShippingAddress() {
        return new CustomerDto(
                null,
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
                null,
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