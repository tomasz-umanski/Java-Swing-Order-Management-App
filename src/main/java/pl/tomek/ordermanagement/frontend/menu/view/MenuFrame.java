package pl.tomek.ordermanagement.frontend.menu.view;

import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class MenuFrame extends JFrame {
    private JButton customersManagementButton;
    private JButton productsManagementButton;
    private JButton ordersManagementButton;

    public MenuFrame() {
        setFrameUp();
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        getRootPane().setBorder(Borders.createEmptyBorder());
        setTitle("Order Management");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(1, 3, 20, 20));
    }

    private void initComponents() {
        customersManagementButton = new JButton("Manage Customers");
        productsManagementButton = new JButton("Manage Products");
        ordersManagementButton = new JButton("Manage Orders");

        add(customersManagementButton);
        add(productsManagementButton);
        add(ordersManagementButton);
    }

    public JButton customersManagementButton() {
        return customersManagementButton;
    }
}