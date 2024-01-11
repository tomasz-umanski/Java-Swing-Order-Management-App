package pl.tomek.ordermanagement.frontend.customer.view.search;

import com.toedter.calendar.JDateChooser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.exception.CustomerCreateDtoValidatorException;
import pl.tomek.ordermanagement.frontend.commons.Borders;

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

@Component
public class CustomerSearchQueryPanel extends JPanel {
    private static final int LAYOUT_ROWS = 3;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -125;
    private static final int VERTICAL_GAP = 20;
    private final JLabel fromDateLabel = new JLabel("Customer's Order Date From");
    private final JDateChooser fromDateChooser = new JDateChooser();
    private final JLabel toDateLabel = new JLabel("Customer's Order Date To");
    private final JDateChooser toDateChooser = new JDateChooser();
    private final JLabel fromValueLabel = new JLabel("Customer's Order Min Value");
    private final JTextField fromValueTextField = createNumberField();

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
        add(fromDateLabel);
        add(fromDateChooser);
        add(toDateLabel);
        add(toDateChooser);
        add(fromValueLabel);
        add(fromValueTextField);
    }

    public CustomerSearchQuery toSearchQuery() {
        Set<String> violations = new HashSet<>();
        BigDecimal fromValue = parseBigDecimal(fromValueTextField.getText(), fromValueLabel.getText(), violations);

        if (!violations.isEmpty())
            throw new CustomerCreateDtoValidatorException(violations);

        return new CustomerSearchQuery(
                convertToLocalDate(fromDateChooser.getDate()),
                convertToLocalDate(toDateChooser.getDate()),
                fromValue
        );
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        if (dateToConvert != null) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null;
    }

    private JTextField createNumberField() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Double.class);
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
