package pl.tomek.ordermanagement.frontend.customer.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.customer.view.search.CustomerSearchPanel;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;
    private final CustomerTablePanel customerTablePanel;
    private final CustomerButtonPanel customerButtonPanel;
    private final CustomerSearchPanel customerSearchPanel;

    @Autowired
    public CustomerFrame(CustomerTablePanel customerTablePanel, CustomerButtonPanel customerButtonPanel, CustomerSearchPanel customerSearchPanel) {
        this.customerTablePanel = customerTablePanel;
        this.customerButtonPanel = customerButtonPanel;
        this.customerSearchPanel = customerSearchPanel;
    }

    @PostConstruct
    private void prepare() {
        setFrameUp();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        setTitle("Customer Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
    }

    private void initComponents() {
        add(customerSearchPanel, BorderLayout.NORTH);
        add(customerTablePanel, BorderLayout.CENTER);
        add(customerButtonPanel, BorderLayout.SOUTH);
    }

    public CustomerButtonPanel customerButtonPanel() {
        return customerButtonPanel;
    }

    public CustomerTablePanel customerTablePanel() {
        return customerTablePanel;
    }

    public CustomerSearchPanel customerSearchPanel() {
        return customerSearchPanel;
    }
}
