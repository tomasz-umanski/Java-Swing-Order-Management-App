package pl.tomek.ordermanagement.feature.product;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tomek.ordermanagement.feature.product.api.Product;
import pl.tomek.ordermanagement.feature.product.api.ProductCreate;
import pl.tomek.ordermanagement.feature.product.api.ProductService;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

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


    private ProductCreate mockProductCreate() {
        return new ProductCreate(
                "Komputer",
                "Super wydajny komputer",
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