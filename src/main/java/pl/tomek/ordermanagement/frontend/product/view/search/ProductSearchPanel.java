package pl.tomek.ordermanagement.frontend.product.view.search;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class ProductSearchPanel extends JPanel {
    private final ProductSearchQueryPanel productSearchQueryPanel;
    private final ProductSearchButtonPanel productSearchButtonPanel;

    @Autowired
    public ProductSearchPanel(ProductSearchQueryPanel productSearchQueryPanel, ProductSearchButtonPanel productSearchButtonPanel) {
        this.productSearchQueryPanel = productSearchQueryPanel;
        this.productSearchButtonPanel = productSearchButtonPanel;
    }

    @PostConstruct
    private void prepare() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        add(productSearchQueryPanel, BorderLayout.CENTER);
        add(productSearchButtonPanel, BorderLayout.SOUTH);
    }

    public ProductSearchQueryPanel productSearchQueryPanel() {
        return productSearchQueryPanel;
    }

    public ProductSearchButtonPanel productSearchButtonPanel() {
        return productSearchButtonPanel;
    }
}
