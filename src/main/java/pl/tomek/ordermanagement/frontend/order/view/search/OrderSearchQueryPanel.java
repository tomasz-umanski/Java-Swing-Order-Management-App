package pl.tomek.ordermanagement.frontend.order.view.search;

import com.toedter.calendar.JDateChooser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.order.exception.OrderCreateDtoValidatorException;
import pl.tomek.ordermanagement.frontend.commons.Borders;
import pl.tomek.ordermanagement.frontend.order.model.CustomerComboBoxModel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class OrderSearchQueryPanel extends JPanel {
    private static final int LAYOUT_ROWS = 5;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -125;
    private static final int VERTICAL_GAP = 20;
    private final JLabel fromDateLabel = new JLabel("Order Date From");
    private final JDateChooser fromDateChooser = new JDateChooser();
    private final JLabel toDateLabel = new JLabel("Order Date To");
    private final JDateChooser toDateChooser = new JDateChooser();
    private final JLabel fromValuePriceLabel = new JLabel("Order Min Price");
    private final JFormattedTextField fromValueTextField = createNumberField();
    private final JLabel toValuePriceLabel = new JLabel("Order Max Price");
    private final JFormattedTextField toValueTextField = createNumberField();
    private final JLabel customerLabel = new JLabel("Customer");
    private JComboBox<CustomerDto> customerComboBox;
    private final CustomerComboBoxModel customerComboBoxModel;

    @Autowired
    public OrderSearchQueryPanel(CustomerComboBoxModel customerComboBoxModel) {
        this.customerComboBoxModel = customerComboBoxModel;
    }

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
        customerComboBox = new JComboBox<>(customerComboBoxModel);

        add(fromDateLabel);
        add(fromDateChooser);
        add(toDateLabel);
        add(toDateChooser);
        add(fromValuePriceLabel);
        add(fromValueTextField);
        add(toValuePriceLabel);
        add(toValueTextField);
        add(customerLabel);
        add(customerComboBox);
    }

    public OrderSearchQuery toSearchQuery() {
        CustomerDto selectedCustomerDto = customerComboBoxModel.getSelectedItem();
        UUID customerId = selectedCustomerDto != null ? selectedCustomerDto.id() : null;
        Set<String> violations = new HashSet<>();
        BigDecimal fromValue = parseBigDecimal(fromValueTextField.getText(), fromValuePriceLabel.getText(), violations);
        BigDecimal toValue = parseBigDecimal(toValueTextField.getText(), toValuePriceLabel.getText(), violations);

        if (!violations.isEmpty())
            throw new OrderCreateDtoValidatorException(violations);

        return new OrderSearchQuery(
                convertToLocalDate(fromDateChooser.getDate()),
                convertToLocalDate(toDateChooser.getDate()),
                fromValue,
                toValue,
                customerId
        );
    }

    public void clearForm() {
        fromDateChooser.setDate(null);
        toDateChooser.setDate(null);
        fromValueTextField.setValue(null);
        toValueTextField.setValue(null);
        customerComboBox.setSelectedIndex(0);
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        if (dateToConvert != null) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null;
    }

    private JFormattedTextField createNumberField() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(true);
        formatter.setMinimum(0.0);
        return new JFormattedTextField(formatter);
    }

    private BigDecimal parseBigDecimal(String text, String fieldName, Set<String> violations) {
        BigDecimal value = null;
        try {
            if (!text.isBlank())
                value = new BigDecimal(text).setScale(2, RoundingMode.CEILING);
        } catch (NumberFormatException e) {
            violations.add(fieldName + ": " + e.getMessage() + "\n");
        }
        return value;
    }
}
