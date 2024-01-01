package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CustomerAdditionButtonPanel extends JPanel {
    private JButton nextButton;
    private JButton cancelButton;

    @PostConstruct
    private void preparePanel() {
        initComponents();
    }

    private void initComponents() {
        nextButton = new JButton("Next");
        add(nextButton);

        cancelButton = new JButton("Cancel");
        add(cancelButton);
    }

    public JButton nextButton() {
        return nextButton;
    }

    public JButton cancelButton() {
        return cancelButton;
    }
}
