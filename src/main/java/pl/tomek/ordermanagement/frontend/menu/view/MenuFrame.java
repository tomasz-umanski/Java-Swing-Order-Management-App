package pl.tomek.ordermanagement.frontend.menu.view;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class MenuFrame extends JFrame {
    private JButton customersManagementButton;
    private JButton productsManagementButton;
    private JButton ordersManagementButton;

    @PostConstruct
    private void prepare() {
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

    public JButton productsManagementButton() {
        return productsManagementButton;
    }

    public JButton ordersManagementButton() {
        return ordersManagementButton;
    }
}