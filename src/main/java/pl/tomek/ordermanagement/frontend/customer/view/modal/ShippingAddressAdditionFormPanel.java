package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
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
        sameAsHomeAddressCheckbox = new JCheckBox("Same as Home Address");
        final JLabel streetNameLabel = new JLabel("Street Name");
        final JLabel buildingNumberLabel = new JLabel("Building Number");
        final JLabel flatNumberLabel = new JLabel("Flat Number");
        final JLabel cityLabel = new JLabel("City");
        final JLabel zipCodeLabel = new JLabel("Zip Code");
        final JLabel voivodeshipLabel = new JLabel("Voivodeship");
        final JLabel countryLabel = new JLabel("Country");

        streetNameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        buildingNumberTextField = new JTextField(TEXT_FIELD_COLUMNS);
        flatNumberTextField = new JTextField(TEXT_FIELD_COLUMNS);
        cityTextField = new JTextField(TEXT_FIELD_COLUMNS);
        zipCodeTextField = new JTextField(TEXT_FIELD_COLUMNS);
        voivodeshipTextField = new JTextField(TEXT_FIELD_COLUMNS);
        countryTextField = new JTextField(TEXT_FIELD_COLUMNS);

        add(new JLabel("Shipping Address:"));
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
    }
}
