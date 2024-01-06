package pl.tomek.ordermanagement.frontend.orderItem.view;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class OrderItemButtonPanel extends JPanel {
    private JButton addButton;
    private JButton removeButton;

    @PostConstruct
    private void preparePanel() {
        initComponents();
    }

    private void initComponents() {
        addButton = new JButton("Add");
        add(addButton);

        removeButton = new JButton("Remove");
        add(removeButton);
    }

    public JButton addButton() {
        return addButton;
    }

    public JButton removeButton() {
        return removeButton;
    }
}
