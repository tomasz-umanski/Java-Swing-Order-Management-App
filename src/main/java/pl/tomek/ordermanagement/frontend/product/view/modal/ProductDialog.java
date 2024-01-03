package pl.tomek.ordermanagement.frontend.product.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;

import javax.swing.*;
import java.awt.*;

@Component
public class ProductDialog extends JFrame {
    private final ProductFormPanel productFormPanel;
    private final ProductModalButtonPanel productModalButtonPanel;

    @Autowired
    public ProductDialog(ProductFormPanel productFormPanel, ProductModalButtonPanel productModalButtonPanel) {
        this.productFormPanel = productFormPanel;
        this.productModalButtonPanel = productModalButtonPanel;
    }

    @PostConstruct
    private void prepareFrame() {
        setFrameUp();
        initComponents();
    }

    public void prepareAddDialog() {
        prepareAddComponents();
        pack();
        setLocationRelativeTo(null);
    }

    public void prepareDetailsDialog(ProductDto productDto) {
        prepareDetailsComponents(productDto);
        pack();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        setTitle("Product Addition");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        add(productFormPanel, BorderLayout.CENTER);
        add(productModalButtonPanel, BorderLayout.SOUTH);
    }

    private void prepareAddComponents() {
        productModalButtonPanel.setVisible(true);
        productFormPanel.prepareAddPanel();
    }

    private void prepareDetailsComponents(ProductDto productDto) {
        productModalButtonPanel.setVisible(false);
        productFormPanel.prepareDetailsPanel(productDto);
    }

    public ProductFormPanel productAdditionFormPanel() {
        return productFormPanel;
    }

    public ProductModalButtonPanel productAdditionButtonPanel() {
        return productModalButtonPanel;
    }
}
