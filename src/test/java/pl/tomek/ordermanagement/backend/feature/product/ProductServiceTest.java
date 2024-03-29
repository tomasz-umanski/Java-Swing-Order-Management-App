package pl.tomek.ordermanagement.backend.feature.product;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomek.ordermanagement.backend.feature.product.api.Product;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductCreate;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductService;
import pl.tomek.ordermanagement.backend.feature.product.exception.ProductCreateValidatorException;
import pl.tomek.ordermanagement.utils.BaseTest;
import pl.tomek.ordermanagement.utils.UnitTest;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService service;

    @Test
    void shouldSaveAssetAndAssignAnId() {
        ProductCreate productCreate = mockProductCreate();

        Product createdProduct = service.create(productCreate);
        assertNotNull(createdProduct.id());
    }

    @Test
    void shouldSaveAssetAndRetrieveBasedOnId() {
        ProductCreate productCreate = mockProductCreate();

        Product createdProduct = service.create(productCreate);
        assertNotNull(createdProduct.id());

        Product retrievedProduct = service.getById(createdProduct.id());
        assertNotNull(retrievedProduct);
        assertEquals(retrievedProduct.SKU(), productCreate.SKU());
    }

    @Test
    void shouldSaveAssetsAndRetrieveAll() {
        ProductCreate productCreate = mockProductCreate();
        ProductCreate secondProductCreate = mockProductCreate();

        service.create(productCreate);
        service.create(secondProductCreate);

        Set<Product> retrievedProductSet = service.getAll();

        assertNotNull(retrievedProductSet);
        assertEquals(retrievedProductSet.size(), 2);
    }

    @Test
    void shouldSaveAndDeleteAsset() {
        ProductCreate productCreate = mockProductCreate();

        Product createdProduct = service.create(productCreate);
        assertNotNull(createdProduct.id());

        service.delete(createdProduct.id());

        assertThrows(EntityNotFoundException.class, () -> service.getById(createdProduct.id()));
    }

    @Test
    void shouldSaveAssetsAndRetrieveOnlyMatchingNames() {
        String namePattern = "book";

        ProductCreate productCreate = mockProductCreate();
        ProductCreate secondProductCreate = mockProductCreate();
        ProductCreate thirdProductCreate = mockProductCreateWithName("TestName");

        service.create(productCreate);
        service.create(secondProductCreate);
        service.create(thirdProductCreate);

        Set<Product> retrievedProductSet = service.get(namePattern);

        assertNotNull(retrievedProductSet);
        assertEquals(2, retrievedProductSet.size());
    }

    @Test
    void shouldThrowValidatorException() {
        ProductCreate productCreate = mockProductCreateWithoutName();
        assertThrows(ProductCreateValidatorException.class, () -> service.create(productCreate));
    }

    private ProductCreate mockProductCreate() {
        return new ProductCreate(
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

    private ProductCreate mockProductCreateWithName(String name) {
        return new ProductCreate(
                name,
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

    private ProductCreate mockProductCreateWithoutName() {
        return new ProductCreate(
                "",
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
}