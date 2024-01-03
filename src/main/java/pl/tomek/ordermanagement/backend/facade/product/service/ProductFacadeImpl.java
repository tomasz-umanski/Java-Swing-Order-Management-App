package pl.tomek.ordermanagement.backend.facade.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductCreateDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductFacade;
import pl.tomek.ordermanagement.backend.facade.product.exception.ProductCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductService;
import pl.tomek.ordermanagement.backend.validation.ObjectsValidator;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class ProductFacadeImpl implements ProductFacade {
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ObjectsValidator<ProductCreateDto> validator;

    @Autowired
    public ProductFacadeImpl(ProductService productService,
                             OrderItemService orderItemService,
                             OrderService orderService,
                             ObjectsValidator<ProductCreateDto> validator) {
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.validator = validator;
    }

    @Override
    public ProductDto saveProduct(ProductCreateDto productCreateDto) {
        Set<String> violations = validator.validate(productCreateDto);
        if (!violations.isEmpty()) {
            throw new ProductCreateDtoValidatorException(violations);
        }
        try {
            return ProductDto.of(productService.create(productCreateDto.toDomainCreate()));
        } catch (DataIntegrityViolationException e) {
            throw new ProductCreateDtoValidatorException(e.getMessage());
        }
    }

    @Override
    public void deleteProduct(UUID id) {
        deleteOrdersWithProduct(id);
        productService.delete(id);
    }

    private void deleteOrdersWithProduct(UUID productId) {
        Set<UUID> orderIds = orderItemService.getByProductId(productId).stream()
                .map(OrderItem::orderId)
                .collect(Collectors.toSet());
        orderIds.forEach(this::deleteOrderItemsAndOrder);
    }

    private void deleteOrderItemsAndOrder(UUID orderId) {
        deleteOrderItemForOrder(orderId);
        orderService.delete(orderId);
    }

    private void deleteOrderItemForOrder(UUID orderId) {
        orderItemService.getByOrderId(orderId).forEach(orderItem -> orderItemService.delete(orderItem.id()));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productService.getAll().stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsContainingNamePattern(String namePattern) {
        return productService.get(namePattern).stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());
    }
}
