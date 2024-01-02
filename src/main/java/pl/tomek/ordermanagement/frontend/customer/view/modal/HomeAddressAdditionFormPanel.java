package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class HomeAddressAdditionFormPanel extends JPanel {
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
    private void preparePanel() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initComponents() {
        streetNameTextField = createTextField();
        buildingNumberTextField = createTextField();
        flatNumberTextField = createTextField();
        cityTextField = createTextField();
        zipCodeTextField = createTextField();
        voivodeshipTextField = createTextField();
        countryTextField = createTextField();

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
        add(new JLabel());
        add(new JLabel());
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
