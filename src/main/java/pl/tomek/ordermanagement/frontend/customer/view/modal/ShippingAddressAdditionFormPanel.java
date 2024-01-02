package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class ShippingAddressAdditionFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -100;
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
    private void preparePanel() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initComponents() {
        initAddressComponent();
    }

    private void initAddressComponent() {
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

        add(new JLabel("Shipping Address:"));
        add(new JLabel());
        add(addShippingAddressCheckbox);
        add(new JLabel());
        add(sameAsHomeAddressCheckbox);
        add(new JLabel());
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

        sameAsHomeAddressCheckbox().setVisible(false);
        setFieldsVisibility(false, false);
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
        streetNameLabel.setEnabled(enabled);

        streetNameTextField.setVisible(visible);
        streetNameTextField.setEnabled(enabled);

        buildingNumberLabel.setVisible(visible);
        buildingNumberLabel.setEnabled(enabled);

        buildingNumberTextField.setVisible(visible);
        buildingNumberTextField.setEnabled(enabled);

        flatNumberLabel.setVisible(visible);
        flatNumberLabel.setEnabled(enabled);

        flatNumberTextField.setVisible(visible);
        flatNumberTextField.setEnabled(enabled);

        cityLabel.setVisible(visible);
        cityLabel.setEnabled(enabled);

        cityTextField.setVisible(visible);
        cityTextField.setEnabled(enabled);

        zipCodeLabel.setVisible(visible);
        zipCodeLabel.setEnabled(enabled);

        zipCodeTextField.setVisible(visible);
        zipCodeTextField.setEnabled(enabled);

        voivodeshipLabel.setVisible(visible);
        voivodeshipLabel.setEnabled(enabled);

        voivodeshipTextField.setVisible(visible);
        voivodeshipTextField.setEnabled(enabled);

        countryLabel.setVisible(visible);
        countryLabel.setEnabled(enabled);

        countryTextField.setVisible(visible);
        countryTextField.setEnabled(enabled);
    }

    public JCheckBox addShippingAddressCheckbox() {
        return addShippingAddressCheckbox;
    }

    public JCheckBox sameAsHomeAddressCheckbox() {
        return sameAsHomeAddressCheckbox;
    }

    public AddressDto toDto() {
        return new AddressDto(
                null,
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
