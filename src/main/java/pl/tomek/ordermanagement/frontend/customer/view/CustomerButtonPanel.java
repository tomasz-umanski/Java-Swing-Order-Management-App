package pl.tomek.ordermanagement.frontend.customer.view;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CustomerButtonPanel extends JPanel {
    private JButton addButton;
    private JButton deleteButton;
    private JButton detailsButton;

    @PostConstruct
    private void prepare() {
        initComponents();
    }

    private void initComponents() {
        addButton = new JButton("Add Customer");
        add(addButton);

        deleteButton = new JButton("Delete Customer");
        add(deleteButton);

        detailsButton = new JButton("Show Customer Details");
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
