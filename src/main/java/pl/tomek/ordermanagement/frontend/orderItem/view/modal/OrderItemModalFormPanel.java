package pl.tomek.ordermanagement.frontend.orderItem.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderItemCreateDto;
import pl.tomek.ordermanagement.backend.facade.order.exception.OrderCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;
import pl.tomek.ordermanagement.frontend.orderItem.model.ProductComboBoxModel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Component
public class OrderItemModalFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 5;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = 0;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;
    private final JLabel quantityLabel = new JLabel("Quantity");
    private final JLabel discountLabel = new JLabel("Discount");
    private final JLabel netPriceLabel = new JLabel("Net Price");
    private final JLabel grossPriceLabel = new JLabel("Gross Price");
    private final JFormattedTextField quantityTextField = createNumberField();
    private final JFormattedTextField discountTextField = createNumberField();
    private final JFormattedTextField netPriceTextField = createNumberField();
    private final JFormattedTextField grossPriceTextField = createNumberField();
    private final JLabel productLabel = new JLabel("Product");
    private JComboBox<ProductDto> productComboBox;
    private final ProductComboBoxModel productComboBoxModel;

    @Autowired
    public OrderItemModalFormPanel(ProductComboBoxModel productComboBoxModel) {
        this.productComboBoxModel = productComboBoxModel;
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
        productComboBox = new JComboBox<>(productComboBoxModel);
        productComboBox.addActionListener(e -> putProposedValuesFromProduct());

        add(productLabel);
        add(productComboBox);
        add(quantityLabel);
        add(quantityTextField);
        add(discountLabel);
        add(discountTextField);
        add(netPriceLabel);
        add(netPriceTextField);
        add(grossPriceLabel);
        add(grossPriceTextField);
    }

    public void putProposedValuesFromProduct() {
        ProductDto productDto = productComboBoxModel.getSelectedItem();
        if (productDto != null) {
            netPriceTextField.setText(productDto.estimatedNetUnitPrice().toString());
            grossPriceTextField.setText(productDto.estimatedGrossUnitPrice().toString());
        }
    }

    public void prepareAddPanel() {
        setFieldsEnabled(true);
        clearForm();
    }

    private void setFieldsEnabled(boolean enabled) {
        productComboBox.setEnabled(enabled);
        quantityTextField.setEnabled(enabled);
        discountTextField.setEnabled(enabled);
        netPriceTextField.setEnabled(enabled);
        grossPriceTextField.setEnabled(enabled);
    }

    public void clearForm() {
        quantityTextField.setValue(null);
        discountTextField.setValue(null);
        netPriceTextField.setValue(null);
        grossPriceTextField.setValue(null);
    }

    public OrderItemCreateDto toCreateDto() {
        int selectedIndex = productComboBox.getSelectedIndex();
        ProductDto productDto = productComboBox.getItemAt(selectedIndex);

        Set<String> violations = new HashSet<>();
        BigDecimal quantity = parseBigDecimal(quantityTextField.getText(), quantityLabel.getText(), violations);
        BigDecimal discount = parseBigDecimal(discountTextField.getText(), discountLabel.getText(), violations);
        BigDecimal netPrice = parseBigDecimal(netPriceTextField.getText(), netPriceLabel.getText(), violations);
        BigDecimal grossPrice = parseBigDecimal(grossPriceTextField.getText(), grossPriceLabel.getText(), violations);

        if (!violations.isEmpty())
            throw new OrderCreateDtoValidatorException(violations);

        return new OrderItemCreateDto(
                productDto,
                quantity,
                discount,
                netPrice,
                grossPrice
        );
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
