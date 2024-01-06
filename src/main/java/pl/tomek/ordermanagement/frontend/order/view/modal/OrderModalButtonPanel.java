package pl.tomek.ordermanagement.frontend.order.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class OrderModalButtonPanel extends JPanel {
    private JButton saveButton;
    private JButton cancelButton;

    @PostConstruct
    private void preparePanel() {
        initComponents();
    }

    private void initComponents() {
        saveButton = new JButton("Save");
        add(saveButton);

        cancelButton = new JButton("Cancel");
        add(cancelButton);
    }

    public JButton saveButton() {
        return saveButton;
    }

    public JButton cancelButton() {
        return cancelButton;
    }
}
