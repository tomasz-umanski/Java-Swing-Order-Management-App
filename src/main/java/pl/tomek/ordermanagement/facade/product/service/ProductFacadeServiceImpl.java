package pl.tomek.ordermanagement.facade.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.facade.product.api.ProductFacadeService;
import pl.tomek.ordermanagement.feature.order.api.OrderService;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItem;
import pl.tomek.ordermanagement.feature.orderItem.api.OrderItemService;
import pl.tomek.ordermanagement.feature.product.api.Product;
import pl.tomek.ordermanagement.feature.product.api.ProductService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class ProductFacadeServiceImpl implements ProductFacadeService {
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @Autowired
    public ProductFacadeServiceImpl(ProductService productService, OrderItemService orderItemService, OrderService orderService) {
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
