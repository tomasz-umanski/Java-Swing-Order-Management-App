package pl.tomek.ordermanagement.frontend.customer;

import pl.tomek.ordermanagement.frontend.MenuFrame;

import javax.swing.*;
import java.awt.*;

public class ManageCustomersFrame extends JFrame {
    private final MenuFrame menuFrame;

    public ManageCustomersFrame(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
        setFrameLayout();
        setButtons();
        centerWindow();
    }

    private void setFrameLayout() {
        setTitle("Manage Customers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new GridLayout(4, 1));
    }

    private void setButtons() {
        initializeAddButton();
        initializeDisplayButton();
        initializeDeleteButton();
        initializeBackButton();
    }

    private void initializeAddButton() {
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Dodawanie klienta"));
        add(addCustomerButton);
    }

    private void initializeDisplayButton() {
        JButton displayCustomersButton = new JButton("Display Customers");
        displayCustomersButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Wyświetlanie klientów"));
        add(displayCustomersButton);
    }

    private void initializeDeleteButton() {
        JButton deleteCustomerButton = new JButton("Delete Customer");
        deleteCustomerButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Usuwanie klienta..."));
        add(deleteCustomerButton);
    }

    private void initializeBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            setVisible(false);
            menuFrame.setVisible(true);
        });
        add(backButton);
    }

    private void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }
}
