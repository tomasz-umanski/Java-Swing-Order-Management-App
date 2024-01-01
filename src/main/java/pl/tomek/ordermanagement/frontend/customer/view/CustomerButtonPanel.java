package pl.tomek.ordermanagement.frontend.customer.view;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CustomerButtonPanel extends JPanel {
    private JButton addButton;
    private JButton deleteButton;

    @PostConstruct
    private void prepare() {
        initComponents();
    }

    private void initComponents() {
        addButton = new JButton("Add Customer");
        add(addButton);

        deleteButton = new JButton("Delete Customer");
        add(deleteButton);
    }

    public JButton addButton() {
        return addButton;
    }

    public JButton deleteButton() {
        return deleteButton;
    }
}
