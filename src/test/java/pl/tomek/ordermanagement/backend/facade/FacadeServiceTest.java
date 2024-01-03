package pl.tomek.ordermanagement.backend.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacade;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductCreateDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductFacade;
import pl.tomek.ordermanagement.utils.BaseTest;
import pl.tomek.ordermanagement.utils.UnitTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class FacadeServiceTest extends BaseTest {

    @Autowired
    private CustomerFacade customerService;
    @Autowired
    private ProductFacade productService;

    @Test
    void shouldSaveProduct() {
        ProductCreateDto productCreateDto = mockProductCreateDto();
        ProductDto createdProductDto = productService.saveProduct(productCreateDto);
        assertNotNull(createdProductDto.id());
        assertEquals(productCreateDto.name(), createdProductDto.name());
    }

    @Test
    void shouldSaveAndDeleteProduct() {
        ProductCreateDto productCreateDto = mockProductCreateDto();
        ProductDto createdProductDto = productService.saveProduct(productCreateDto);
        assertNotNull(createdProductDto.id());
        productService.deleteProduct(createdProductDto.id());
    }

    @Test
    void shouldSaveCustomer() {
        CustomerCreateDto customerCreateDto = mockCustomerCreateDto();
        CustomerDto createdCustomerDto = customerService.saveCustomer(customerCreateDto);
        assertNotNull(createdCustomerDto.id());
        assertNotNull(createdCustomerDto.homeAddress().id());
        assertNotNull(createdCustomerDto.shippingAddress().id());
        assertEquals(customerCreateDto.name(), createdCustomerDto.name());
    }

    @Test
    void shouldSaveCustomerWithoutShippingAddress() {
        CustomerCreateDto customerCreateDto = mockCustomerCreateDtoWithoutShippingAddress();
        CustomerDto createdCustomerDto = customerService.saveCustomer(customerCreateDto);
        assertNotNull(createdCustomerDto.id());
        assertNotNull(createdCustomerDto.homeAddress().id());
        assertNull(createdCustomerDto.shippingAddress());
        assertEquals(customerCreateDto.name(), createdCustomerDto.name());
    }

    @Test
    void shouldSaveAndDeleteCustomer() {
        CustomerCreateDto customerCreateDto = mockCustomerCreateDto();
        CustomerDto createdCustomerDto = customerService.saveCustomer(customerCreateDto);
        assertNotNull(createdCustomerDto.id());
        customerService.deleteCustomer(createdCustomerDto);
    }

    @Test
    void shouldSaveCustomersAndRetrieveAll() {
        CustomerCreateDto customerCreateDto = mockCustomerCreateDto();
        CustomerCreateDto customerCreateDtoWithoutShippingAddress = mockCustomerCreateDtoWithoutShippingAddress();
        customerService.saveCustomer(customerCreateDto);
        customerService.saveCustomer(customerCreateDtoWithoutShippingAddress);

        List<CustomerDto> retrievedCustomerSet = customerService.getAllCustomers();

        assertNotNull(retrievedCustomerSet);
        assertEquals(retrievedCustomerSet.size(), 2);
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

    private ProductCreateDto mockProductCreateDto() {
        return new ProductCreateDto(
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

    private CustomerCreateDto mockCustomerCreateDto() {
        return new CustomerCreateDto(
                "Jan",
                "Testowy",
                "Firma",
                "1234563218",
                mockAddressCreateDto(),
                mockAddressCreateDto()
        );
    }

    private CustomerCreateDto mockCustomerCreateDtoWithoutShippingAddress() {
        return new CustomerCreateDto(
                "Jan",
                "Testowy",
                "Firma",
                "1234563218",
                mockAddressCreateDto(),
                null
        );
    }

    private AddressCreateDto mockAddressCreateDto() {
        return new AddressCreateDto(
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