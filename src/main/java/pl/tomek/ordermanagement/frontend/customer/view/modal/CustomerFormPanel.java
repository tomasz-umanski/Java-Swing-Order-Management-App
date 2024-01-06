package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -100;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;
    private final JTextField nameTextField = createTextField();
    private final JTextField lastNameTextField = createTextField();
    private final JTextField companyNameTextField = createTextField();
    private final JTextField taxIdNumberTextField = createTextField();

    @PostConstruct
    private void prepare() {
        setPanelUp();
        initComponents();
    }

    private void initComponents() {
        add(new JLabel("Customer Details:"));
        add(new JLabel());
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
    }

    public void prepareAddPanel() {
        initAddComponents();
    }

    public void prepareDetailsPanel(CustomerDto customerDto) {
        initDetailsComponents(customerDto);
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initDetailsComponents(CustomerDto customerDto) {
        nameTextField.setEnabled(false);
        nameTextField.setText(customerDto.name());
        lastNameTextField.setEnabled(false);
        lastNameTextField.setText(customerDto.lastName());
        companyNameTextField.setEnabled(false);
        companyNameTextField.setText(customerDto.companyName());
        taxIdNumberTextField.setEnabled(false);
        taxIdNumberTextField.setText(customerDto.taxIdNumber());
    }

    private void initAddComponents() {
        nameTextField.setEnabled(true);
        lastNameTextField.setEnabled(true);
        companyNameTextField.setEnabled(true);
        taxIdNumberTextField.setEnabled(true);
        clearForm();
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

    public CustomerCreateDto toCreateDto(AddressCreateDto homeAddressCreateDto, AddressCreateDto shippingAddressCreateDto) {
        return new CustomerCreateDto(
                nameTextField.getText().trim(),
                lastNameTextField.getText().trim(),
                companyNameTextField.getText().trim(),
                taxIdNumberTextField.getText().trim(),
                homeAddressCreateDto,
                shippingAddressCreateDto
        );
    }
}
