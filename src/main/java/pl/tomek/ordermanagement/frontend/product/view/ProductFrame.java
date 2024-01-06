package pl.tomek.ordermanagement.frontend.product.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.product.view.search.ProductSearchPanel;

import javax.swing.*;
import java.awt.*;

@Component
public class ProductFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;
    private final ProductTablePanel productTablePanel;
    private final ProductButtonPanel productButtonPanel;
    private final ProductSearchPanel productSearchPanel;

    @Autowired
    public ProductFrame(ProductTablePanel productTablePanel, ProductButtonPanel productButtonPanel, ProductSearchPanel productSearchPanel) {
        this.productTablePanel = productTablePanel;
        this.productButtonPanel = productButtonPanel;
        this.productSearchPanel = productSearchPanel;
    }

    @PostConstruct
    private void prepare() {
        setFrameUp();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        setTitle("Product Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
    }

    private void initComponents() {
        add(productSearchPanel, BorderLayout.NORTH);
        add(productTablePanel, BorderLayout.CENTER);
        add(productButtonPanel, BorderLayout.SOUTH);
    }

    public ProductTablePanel productTablePanel() {
        return productTablePanel;
    }

    public ProductButtonPanel productButtonPanel() {
        return productButtonPanel;
    }

    public ProductSearchPanel productSearchPanel() {
        return productSearchPanel;
    }
}
