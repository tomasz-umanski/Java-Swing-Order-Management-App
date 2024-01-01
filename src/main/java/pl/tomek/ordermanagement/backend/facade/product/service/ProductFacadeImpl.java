package pl.tomek.ordermanagement.backend.facade.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductFacade;
import pl.tomek.ordermanagement.backend.feature.order.api.OrderService;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.backend.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.backend.feature.product.api.Product;
import pl.tomek.ordermanagement.backend.feature.product.api.ProductService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class ProductFacadeImpl implements ProductFacade {
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @Autowired
    public ProductFacadeImpl(ProductService productService, OrderItemService orderItemService, OrderService orderService) {
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        return ProductDto.of(productService.create(productDto.toCreate()));
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
    public Set<Product> getAllProducts() {
        return productService.getAll();
    }

    @Override
    public Set<Product> getProductsContainingNamePattern(String namePattern) {
        return productService.get(namePattern);
    }
}
