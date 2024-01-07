package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerDialog extends JFrame {
    private final CustomerFormPanel customerFormPanel;
    private final HomeAddressFormPanel homeAddressFormPanel;
    private final ShippingAddressFormPanel shippingAddressFormPanel;
    private final CustomerAdditionButtonPanel customerAdditionButtonPanel;
    private final CustomerOrderTablePanel customerOrderTablePanel;

    @Autowired
    public CustomerDialog(CustomerFormPanel customerFormPanel,
                          CustomerAdditionButtonPanel customerAdditionButtonPanel,
                          HomeAddressFormPanel homeAddressFormPanel,
                          ShippingAddressFormPanel shippingAddressFormPanel, CustomerOrderTablePanel customerOrderTablePanel) {
        this.customerFormPanel = customerFormPanel;
        this.homeAddressFormPanel = homeAddressFormPanel;
        this.shippingAddressFormPanel = shippingAddressFormPanel;
        this.customerAdditionButtonPanel = customerAdditionButtonPanel;
        this.customerOrderTablePanel = customerOrderTablePanel;
    }

    @PostConstruct
    private void prepare() {
        setFrameUp();
        initComponents();
    }

    private void initComponents() {
        add(customerFormPanel, BorderLayout.WEST);
        add(homeAddressFormPanel, BorderLayout.CENTER);
        add(shippingAddressFormPanel, BorderLayout.EAST);
    }

    public void prepareAddDialog() {
        prepareAddComponents();
        pack();
        setLocationRelativeTo(null);
    }

    public void prepareDetailsDialog(CustomerDto customerDto) {
        prepareDetailsComponents(customerDto);
        pack();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        setTitle("Customer");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void prepareAddComponents() {
        remove(customerOrderTablePanel);
        add(customerAdditionButtonPanel, BorderLayout.SOUTH);
        customerFormPanel.prepareAddPanel();
        homeAddressFormPanel.prepareAddPanel();
        shippingAddressFormPanel.prepareAddPanel();
    }

    private void prepareDetailsComponents(CustomerDto customerDto) {
        remove(customerAdditionButtonPanel);
        add(customerOrderTablePanel, BorderLayout.SOUTH);

        customerFormPanel.prepareDetailsPanel(customerDto);
        homeAddressFormPanel.prepareDetailsPanel(customerDto.homeAddress());
        shippingAddressFormPanel.prepareDetailsPanel(customerDto.shippingAddress());
    }

    public CustomerFormPanel customerAdditionFormPanel() {
        return customerFormPanel;
    }

    public HomeAddressFormPanel homeAddressAdditionFormPanel() {
        return homeAddressFormPanel;
    }

    public ShippingAddressFormPanel shippingAddressAdditionFormPanel() {
        return shippingAddressFormPanel;
    }

    public CustomerAdditionButtonPanel customerAdditionButtonPanel() {
        return customerAdditionButtonPanel;
    }
}
