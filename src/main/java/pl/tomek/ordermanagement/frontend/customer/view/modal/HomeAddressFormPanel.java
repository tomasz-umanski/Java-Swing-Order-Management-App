package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class HomeAddressFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -100;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;
    private JTextField streetNameTextField;
    private JTextField buildingNumberTextField;
    private JTextField flatNumberTextField;
    private JTextField cityTextField;
    private JTextField zipCodeTextField;
    private JTextField voivodeshipTextField;
    private JTextField countryTextField;

    @PostConstruct
    private void prepare() {
        setPanelUp();
        initComponents();
    }

    private void initComponents() {
        streetNameTextField = createTextField();
        buildingNumberTextField = createTextField();
        flatNumberTextField = createTextField();
        cityTextField = createTextField();
        zipCodeTextField = createTextField();
        voivodeshipTextField = createTextField();
        countryTextField = createTextField();

        add(new JLabel("Home Address:"));
        add(new JLabel());
        add(new JLabel("Street Name"));
        add(streetNameTextField);
        add(new JLabel("Building Number"));
        add(buildingNumberTextField);
        add(new JLabel("Flat Number"));
        add(flatNumberTextField);
        add(new JLabel("City"));
        add(cityTextField);
        add(new JLabel("Zip Code"));
        add(zipCodeTextField);
        add(new JLabel("Voivodeship"));
        add(voivodeshipTextField);
        add(new JLabel("Country"));
        add(countryTextField);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
    }

    public void prepareAddPanel() {
        initAddComponents();
    }

    private void initAddComponents() {
        streetNameTextField.setEnabled(true);
        buildingNumberTextField.setEnabled(true);
        flatNumberTextField.setEnabled(true);
        cityTextField.setEnabled(true);
        zipCodeTextField.setEnabled(true);
        voivodeshipTextField.setEnabled(true);
        countryTextField.setEnabled(true);
        clearForm();
    }

    public void prepareDetailsPanel(AddressDto addressDto) {
        initDetailsComponents(addressDto);
    }

    private void initDetailsComponents(AddressDto addressDto) {
        streetNameTextField.setEnabled(false);
        streetNameTextField.setText(addressDto.streetName());
        buildingNumberTextField.setEnabled(false);
        buildingNumberTextField.setText(addressDto.buildingNumber());
        flatNumberTextField.setEnabled(false);
        flatNumberTextField.setText(addressDto.flatNumber());
        cityTextField.setEnabled(false);
        cityTextField.setText(addressDto.city());
        zipCodeTextField.setEnabled(false);
        zipCodeTextField.setText(addressDto.zipCode());
        voivodeshipTextField.setEnabled(false);
        voivodeshipTextField.setText(addressDto.voivodeship());
        countryTextField.setEnabled(false);
        countryTextField.setText(addressDto.country());
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private JTextField createTextField() {
        return new JTextField(TEXT_FIELD_COLUMNS);
    }

    public void clearForm() {
        clearTextField(streetNameTextField);
        clearTextField(buildingNumberTextField);
        clearTextField(flatNumberTextField);
        clearTextField(cityTextField);
        clearTextField(zipCodeTextField);
        clearTextField(voivodeshipTextField);
        clearTextField(countryTextField);
    }

    private void clearTextField(JTextField textField) {
        textField.setText("");
    }

    public AddressCreateDto toCreateDto() {
        return new AddressCreateDto(
                streetNameTextField.getText(),
                buildingNumberTextField.getText(),
                flatNumberTextField.getText(),
                cityTextField.getText(),
                zipCodeTextField.getText(),
                voivodeshipTextField.getText(),
                countryTextField.getText()
        );
    }
}
