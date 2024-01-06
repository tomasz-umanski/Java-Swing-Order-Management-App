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

    @Autowired
    public CustomerDialog(CustomerFormPanel customerFormPanel,
                          CustomerAdditionButtonPanel customerAdditionButtonPanel,
                          HomeAddressFormPanel homeAddressFormPanel,
                          ShippingAddressFormPanel shippingAddressFormPanel) {
        this.customerFormPanel = customerFormPanel;
        this.homeAddressFormPanel = homeAddressFormPanel;
        this.shippingAddressFormPanel = shippingAddressFormPanel;
        this.customerAdditionButtonPanel = customerAdditionButtonPanel;
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
        add(customerAdditionButtonPanel, BorderLayout.SOUTH);
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
        customerAdditionButtonPanel.setVisible(true);
        customerFormPanel.prepareAddPanel();
        homeAddressFormPanel.prepareAddPanel();
        shippingAddressFormPanel.prepareAddPanel();
    }

    private void prepareDetailsComponents(CustomerDto customerDto) {
        customerAdditionButtonPanel.setVisible(false);
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
