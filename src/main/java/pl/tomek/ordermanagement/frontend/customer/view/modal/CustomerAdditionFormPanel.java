package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
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
        nameTextField = createTextField();
        lastNameTextField = createTextField();
        companyNameTextField = createTextField();
        taxIdNumberTextField = createTextField();

        add(new JLabel("Name"));
        add(nameTextField);
        add(new JLabel("Last Name"));
        add(lastNameTextField);
        add(new JLabel("Company Name"));
        add(companyNameTextField);
        add(new JLabel("Tax ID Number"));
        add(taxIdNumberTextField);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
    }

    private JTextField createTextField() {
        return new JTextField(TEXT_FIELD_COLUMNS);
    }

    public void clearForm() {
        clearTextField(nameTextField);
        clearTextField(lastNameTextField);
        clearTextField(companyNameTextField);
        clearTextField(taxIdNumberTextField);
    }

    private void clearTextField(JTextField textField) {
        textField.setText("");
    }

    public CustomerDto toDto(AddressDto homeAddressDto, AddressDto shippingAddressDto) {
        return new CustomerDto(
                null,
                nameTextField.getText(),
                lastNameTextField.getText(),
                companyNameTextField.getText(),
                taxIdNumberTextField.getText(),
                homeAddressDto,
                shippingAddressDto
        );
    }
}
