package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class ShippingAddressFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = 0;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;

    final JLabel streetNameLabel = new JLabel("Street Name");
    final JLabel buildingNumberLabel = new JLabel("Building Number");
    final JLabel flatNumberLabel = new JLabel("Flat Number");
    final JLabel cityLabel = new JLabel("City");
    final JLabel zipCodeLabel = new JLabel("Zip Code");
    final JLabel voivodeshipLabel = new JLabel("Voivodeship");
    final JLabel countryLabel = new JLabel("Country");

    private JCheckBox addShippingAddressCheckbox;
    private JCheckBox sameAsHomeAddressCheckbox;
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

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initComponents() {
        addShippingAddressCheckbox = new JCheckBox("Add Shipping Address");
        addShippingAddressCheckbox.addActionListener(e -> onChangedShippingAddressCheckbox());

        sameAsHomeAddressCheckbox = new JCheckBox("Same as Home Address");
        sameAsHomeAddressCheckbox.addActionListener(e -> onChangedSameAsHomeAddressCheckbox());

        streetNameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        buildingNumberTextField = new JTextField(TEXT_FIELD_COLUMNS);
        flatNumberTextField = new JTextField(TEXT_FIELD_COLUMNS);
        cityTextField = new JTextField(TEXT_FIELD_COLUMNS);
        zipCodeTextField = new JTextField(TEXT_FIELD_COLUMNS);
        voivodeshipTextField = new JTextField(TEXT_FIELD_COLUMNS);
        countryTextField = new JTextField(TEXT_FIELD_COLUMNS);

        addFields(true);
    }

    public void addFields(boolean withCheckboxes) {
        add(new JLabel("Shipping Address:"));
        add(new JLabel());
        if (withCheckboxes) {
            add(addShippingAddressCheckbox);
            add(sameAsHomeAddressCheckbox);
        }
        add(streetNameLabel);
        add(streetNameTextField);
        add(buildingNumberLabel);
        add(buildingNumberTextField);
        add(flatNumberLabel);
        add(flatNumberTextField);
        add(cityLabel);
        add(cityTextField);
        add(zipCodeLabel);
        add(zipCodeTextField);
        add(voivodeshipLabel);
        add(voivodeshipTextField);
        add(countryLabel);
        add(countryTextField);
    }

    public void prepareAddPanel() {
        removeAll();
        addFields(true);
        addShippingAddressCheckbox.setVisible(true);
        sameAsHomeAddressCheckbox.setVisible(false);
        clearForm(true);
        setFieldsVisibility(false, false);
    }

    public void prepareDetailsPanel(AddressDto addressDto) {
        removeAll();
        addFields(false);
        addShippingAddressCheckbox.setVisible(false);
        sameAsHomeAddressCheckbox.setVisible(false);
        setFieldsVisibility(true, false);
        if (addressDto != null) {
            setData(addressDto);
        }
    }

    private void setData(AddressDto addressDto) {
        streetNameTextField.setText(addressDto.streetName());
        buildingNumberTextField.setText(addressDto.buildingNumber());
        flatNumberTextField.setText(addressDto.flatNumber());
        cityTextField.setText(addressDto.city());
        zipCodeTextField.setText(addressDto.zipCode());
        voivodeshipTextField.setText(addressDto.voivodeship());
        countryTextField.setText(addressDto.country());
    }

    private void onChangedShippingAddressCheckbox() {
        if (addShippingAddressCheckbox().isSelected()) {
            sameAsHomeAddressCheckbox().setVisible(true);
            setFieldsVisibility(true, true);
        } else {
            clearForm(true);
            sameAsHomeAddressCheckbox().setVisible(false);
            setFieldsVisibility(false, false);
        }
    }

    private void onChangedSameAsHomeAddressCheckbox() {
        if (sameAsHomeAddressCheckbox().isSelected()) {
            setFieldsVisibility(false, false);
        } else {
            clearForm(false);
            setFieldsVisibility(true, true);
        }
    }

    public void clearForm(boolean withAddCheckbox) {
        if (withAddCheckbox)
            addShippingAddressCheckbox.setSelected(false);
        sameAsHomeAddressCheckbox.setSelected(false);
        streetNameTextField.setText(Strings.EMPTY);
        buildingNumberTextField.setText(Strings.EMPTY);
        flatNumberTextField.setText(Strings.EMPTY);
        cityTextField.setText(Strings.EMPTY);
        zipCodeTextField.setText(Strings.EMPTY);
        voivodeshipTextField.setText(Strings.EMPTY);
        countryTextField.setText(Strings.EMPTY);
        setFieldsVisibility(false, false);
    }

    public void setFieldsVisibility(boolean visible, boolean enabled) {
        streetNameLabel.setVisible(visible);

        streetNameTextField.setVisible(visible);
        streetNameTextField.setEnabled(enabled);

        buildingNumberLabel.setVisible(visible);

        buildingNumberTextField.setVisible(visible);
        buildingNumberTextField.setEnabled(enabled);

        flatNumberLabel.setVisible(visible);

        flatNumberTextField.setVisible(visible);
        flatNumberTextField.setEnabled(enabled);

        cityLabel.setVisible(visible);

        cityTextField.setVisible(visible);
        cityTextField.setEnabled(enabled);

        zipCodeLabel.setVisible(visible);

        zipCodeTextField.setVisible(visible);
        zipCodeTextField.setEnabled(enabled);

        voivodeshipLabel.setVisible(visible);

        voivodeshipTextField.setVisible(visible);
        voivodeshipTextField.setEnabled(enabled);

        countryLabel.setVisible(visible);

        countryTextField.setVisible(visible);
        countryTextField.setEnabled(enabled);
    }

    public JCheckBox addShippingAddressCheckbox() {
        return addShippingAddressCheckbox;
    }

    public JCheckBox sameAsHomeAddressCheckbox() {
        return sameAsHomeAddressCheckbox;
    }

    public AddressCreateDto toCreateDto() {
        return new AddressCreateDto(
                streetNameTextField.getText().trim(),
                buildingNumberTextField.getText().trim(),
                flatNumberTextField.getText().trim(),
                cityTextField.getText().trim(),
                zipCodeTextField.getText().trim(),
                voivodeshipTextField.getText().trim(),
                countryTextField.getText().trim()
        );
    }
}
