package pl.tomek.ordermanagement.frontend;

import pl.tomek.ordermanagement.frontend.customer.ManageCustomersFrame;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    public MenuFrame() {
        setFrameLayout();
        setButtons();
        centerWindow();
        setVisible(true);
    }

    private void setFrameLayout() {
        setTitle("Menadżer Aplikacji");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new GridLayout(4, 1));
    }

    private void setButtons() {
        initializeCustomersButton();
        initializeProductButton();
        initializeManageOrdersButton();
        initializeQuitButton();
    }

    private void initializeCustomersButton() {
        JButton manageCustomersButton = new JButton("Manage Customers");
        manageCustomersButton.addActionListener(e -> {
            ManageCustomersFrame manageCustomersFrame = new ManageCustomersFrame(this);
            setVisible(false);
            manageCustomersFrame.setVisible(true);
        });
        add(manageCustomersButton);
    }

    private void initializeProductButton() {
        JButton manageProductsButton = new JButton("Manage Products");
        manageProductsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Manage Products..."));
        add(manageProductsButton);
    }

    private void initializeManageOrdersButton() {
        JButton manageOrdersButton = new JButton("Manage Orders");
        manageOrdersButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Manage Orders..."));
        add(manageOrdersButton);
    }

    private void initializeQuitButton() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);
    }

    private void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }
}