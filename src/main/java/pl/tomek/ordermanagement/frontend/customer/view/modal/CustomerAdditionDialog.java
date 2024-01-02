package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerAdditionDialog extends JDialog {
    private final CustomerAdditionFormPanel customerAdditionFormPanel;
    private final HomeAddressAdditionFormPanel homeAddressAdditionFormPanel;
    private final ShippingAddressAdditionFormPanel shippingAddressAdditionFormPanel;
    private final CustomerAdditionButtonPanel customerAdditionButtonPanel;

    @Autowired
    public CustomerAdditionDialog(CustomerAdditionFormPanel customerAdditionFormPanel,
                                  CustomerAdditionButtonPanel customerAdditionButtonPanel,
                                  HomeAddressAdditionFormPanel homeAddressAdditionFormPanel,
                                  ShippingAddressAdditionFormPanel shippingAddressAdditionFormPanel) {
        this.customerAdditionFormPanel = customerAdditionFormPanel;
        this.homeAddressAdditionFormPanel = homeAddressAdditionFormPanel;
        this.shippingAddressAdditionFormPanel = shippingAddressAdditionFormPanel;
        this.customerAdditionButtonPanel = customerAdditionButtonPanel;
    }

    @PostConstruct
    private void prepareFrame() {
        setFrameUp();
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        setTitle("Customer Addition");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(customerAdditionFormPanel, BorderLayout.WEST);
        add(homeAddressAdditionFormPanel, BorderLayout.CENTER);
        add(shippingAddressAdditionFormPanel, BorderLayout.EAST);
        add(customerAdditionButtonPanel, BorderLayout.SOUTH);
    }

    public CustomerAdditionFormPanel customerAdditionFormPanel() {
        return customerAdditionFormPanel;
    }

    public HomeAddressAdditionFormPanel homeAddressAdditionFormPanel() {
        return homeAddressAdditionFormPanel;
    }

    public ShippingAddressAdditionFormPanel shippingAddressAdditionFormPanel() {
        return shippingAddressAdditionFormPanel;
    }

    public CustomerAdditionButtonPanel customerAdditionButtonPanel() {
        return customerAdditionButtonPanel;
    }
}
