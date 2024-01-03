package pl.tomek.ordermanagement.frontend.product.view;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ProductButtonPanel extends JPanel {
    private JButton addButton;
    private JButton deleteButton;
    private JButton detailsButton;

    @PostConstruct
    private void prepare() {
        initComponents();
    }

    private void initComponents() {
        addButton = new JButton("Add Product");
        add(addButton);

        deleteButton = new JButton("Delete Product");
        add(deleteButton);

        detailsButton = new JButton("Show Product Details");
        add(detailsButton);
    }

    public JButton addButton() {
        return addButton;
    }

    public JButton deleteButton() {
        return deleteButton;
    }

    public JButton detailsButton() {
        return detailsButton;
    }
}
