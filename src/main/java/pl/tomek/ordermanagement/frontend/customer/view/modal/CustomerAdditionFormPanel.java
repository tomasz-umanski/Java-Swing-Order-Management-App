package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerAdditionFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -100;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;

    private JTextField nameTextField;
    private JTextField lastNameTextField;
    private JTextField companyNameTextField;
    private JTextField taxIdNumberTextField;

    @PostConstruct
    private void preparePanel() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initComponents() {
        initCustomerDetailsComponent();
    }

    private void initCustomerDetailsComponent() {
        final JLabel nameLabel = new JLabel("Name");
        final JLabel lastNameLabel = new JLabel("Last name");
        final JLabel companyNameLabel = new JLabel("Company name");
        final JLabel taxIdNumberLabel = new JLabel("Tax id number");

        nameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        lastNameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        companyNameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        taxIdNumberTextField = new JTextField(TEXT_FIELD_COLUMNS);

        add(new JLabel("Customer Details:"));
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(nameLabel);
        add(nameTextField);
        add(lastNameLabel);
        add(lastNameTextField);
        add(companyNameLabel);
        add(companyNameTextField);
        add(taxIdNumberLabel);
        add(taxIdNumberTextField);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
    }
}