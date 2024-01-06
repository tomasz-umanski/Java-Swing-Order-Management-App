package pl.tomek.ordermanagement.frontend.customer.view.search;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerSearchPanel extends JPanel {
    private final CustomerSearchQueryPanel customerSearchQueryPanel;
    private final CustomerSearchButtonPanel customerSearchButtonPanel;

    public CustomerSearchPanel(CustomerSearchQueryPanel customerSearchQueryPanel, CustomerSearchButtonPanel customerSearchButtonPanel) {
        this.customerSearchQueryPanel = customerSearchQueryPanel;
        this.customerSearchButtonPanel = customerSearchButtonPanel;
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
        add(customerSearchQueryPanel, BorderLayout.WEST);
        add(customerSearchButtonPanel, BorderLayout.EAST);
    }

    public CustomerSearchQueryPanel customerSearchQueryPanel() {
        return customerSearchQueryPanel;
    }

    public CustomerSearchButtonPanel customerSearchButtonPanel() {
        return customerSearchButtonPanel;
    }
}
