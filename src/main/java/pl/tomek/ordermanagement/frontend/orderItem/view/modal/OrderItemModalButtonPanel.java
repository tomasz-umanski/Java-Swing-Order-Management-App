package pl.tomek.ordermanagement.frontend.orderItem.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class OrderItemModalButtonPanel extends JPanel {
    private JButton addButton;
    private JButton cancelButton;

    @PostConstruct
    private void preparePanel() {
        initComponents();
    }

    private void initComponents() {
        addButton = new JButton("Add");
        add(addButton);

        cancelButton = new JButton("Cancel");
        add(cancelButton);
    }

    public JButton addButton() {
        return addButton;
    }

    public JButton cancelButton() {
        return cancelButton;
    }
}
