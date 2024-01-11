package pl.tomek.ordermanagement.frontend.product.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductCreateDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.exception.ProductCreateDtoValidatorException;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Component
public class ProductFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -50;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;
    private final JLabel nameLabel = new JLabel("Name");
    private final JLabel descriptionLabel = new JLabel("Description");
    private final JLabel SKULabel = new JLabel("SKU");
    private final JLabel estimatedNetUnitPriceLabel = new JLabel("Estimated Net Unit Price");
    private final JLabel estimatedGrossUnitPriceLabel = new JLabel("Estimated Gross Unit Price");
    private final JLabel lengthLabel = new JLabel("Length (m)");
    private final JLabel heightLabel = new JLabel("Height (m)");
    private final JLabel widthLabel = new JLabel("Width (m)");
    private final JLabel weightLabel = new JLabel("Weight (kg)");
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField SKUTextField;
    private JTextField estimatedNetUnitPriceTextField;
    private JTextField estimatedGrossUnitPriceTextField;
    private JTextField lengthTextField;
    private JTextField heightTextField;
    private JTextField widthTextField;
    private JTextField weightTextField;

    @PostConstruct
    private void preparePanel() {
        setPanelUp();
        initComponents();
    }

    public void prepareAddPanel() {
        initAddComponents();
    }

    public void prepareDetailsPanel(ProductDto productDto) {
        initDetailsComponents(productDto);
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initComponents() {
        nameTextField = createTextField();
        descriptionTextField = createTextField();
        SKUTextField = createTextField();
        estimatedNetUnitPriceTextField = createNumberField();
        estimatedGrossUnitPriceTextField = createNumberField();
        lengthTextField = createNumberField();
        heightTextField = createNumberField();
        widthTextField = createNumberField();
        weightTextField = createNumberField();

        add(nameLabel);
        add(nameTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(SKULabel);
        add(SKUTextField);
        add(estimatedNetUnitPriceLabel);
        add(estimatedNetUnitPriceTextField);
        add(estimatedGrossUnitPriceLabel);
        add(estimatedGrossUnitPriceTextField);
        add(lengthLabel);
        add(lengthTextField);
        add(heightLabel);
        add(heightTextField);
        add(widthLabel);
        add(widthTextField);
        add(weightLabel);
        add(weightTextField);
        add(new JLabel());
        add(new JLabel());
    }

    private void initAddComponents() {
        setFieldsEnabled(true);
        clearForm();
    }

    private void initDetailsComponents(ProductDto productDto) {
        setFieldsEnabled(false);
        setData(productDto);
    }

    private void setFieldsEnabled(boolean enabled) {
        nameTextField.setEnabled(enabled);
        descriptionTextField.setEnabled(enabled);
        SKUTextField.setEnabled(enabled);
        estimatedNetUnitPriceTextField.setEnabled(enabled);
        estimatedGrossUnitPriceTextField.setEnabled(enabled);
        lengthTextField.setEnabled(enabled);
        heightTextField.setEnabled(enabled);
        widthTextField.setEnabled(enabled);
        weightTextField.setEnabled(enabled);
    }

    private void setData(ProductDto productDto) {
        nameTextField.setText(productDto.name());
        descriptionTextField.setText(productDto.description());
        SKUTextField.setText(productDto.SKU());
        estimatedNetUnitPriceTextField.setText(productDto.estimatedNetUnitPrice().toString());
        estimatedGrossUnitPriceTextField.setText(productDto.estimatedGrossUnitPrice().toString());
        if (productDto.length() != null) {
            lengthTextField.setText(productDto.length().toString());
        }
        if (productDto.height() != null) {
            heightTextField.setText(productDto.height().toString());
        }
        if (productDto.width() != null) {
            widthTextField.setText(productDto.width().toString());
        }
        if (productDto.weight() != null) {
            weightTextField.setText(productDto.weight().toString());
        }
    }

    private JTextField createTextField() {
        return new JTextField(TEXT_FIELD_COLUMNS);
    }

    private JTextField createNumberField() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        return new JFormattedTextField(formatter);
    }

    public void clearForm() {
        clearTextField(nameTextField);
        clearTextField(descriptionTextField);
        clearTextField(SKUTextField);
        clearTextField(estimatedNetUnitPriceTextField);
        clearTextField(estimatedGrossUnitPriceTextField);
        clearTextField(lengthTextField);
        clearTextField(heightTextField);
        clearTextField(widthTextField);
        clearTextField(weightTextField);
    }

    private void clearTextField(JTextField textField) {
        textField.setText("");
    }

    public ProductCreateDto toCreateDto() {
        Set<String> violations = new HashSet<>();
        BigDecimal estimatedNetUnitPrice = parseBigDecimal(estimatedNetUnitPriceTextField.getText().trim(), estimatedNetUnitPriceLabel.getText(), violations);
        BigDecimal estimatedGrossUnitPrice = parseBigDecimal(estimatedGrossUnitPriceTextField.getText().trim(), estimatedGrossUnitPriceLabel.getText(), violations);
        BigDecimal length = parseBigDecimal(lengthTextField.getText().trim(), lengthLabel.getText(), violations);
        BigDecimal height = parseBigDecimal(heightTextField.getText().trim(), heightLabel.getText(), violations);
        BigDecimal width = parseBigDecimal(widthTextField.getText().trim(), widthLabel.getText(), violations);
        BigDecimal weight = parseBigDecimal(weightTextField.getText().trim(), weightLabel.getText(), violations);

        if (!violations.isEmpty())
            throw new ProductCreateDtoValidatorException(violations);

        return new ProductCreateDto(
                nameTextField.getText().trim(),
                descriptionTextField.getText().trim(),
                SKUTextField.getText().trim(),
                estimatedNetUnitPrice,
                estimatedGrossUnitPrice,
                length,
                height,
                width,
                weight
        );
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
