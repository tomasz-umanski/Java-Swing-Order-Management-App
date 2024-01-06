package pl.tomek.ordermanagement.frontend.product.view.search;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class ProductSearchQueryPanel extends JPanel {
    private static final int LAYOUT_ROWS = 1;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = -125;
    private static final int VERTICAL_GAP = 20;
    private static final int TEXT_FIELD_COLUMNS = 20;

    private final JLabel nameLikeLabel = new JLabel("Name like");
    private final JTextField nameLikeTextField = new JTextField(TEXT_FIELD_COLUMNS);

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
        add(nameLikeLabel);
        add(nameLikeTextField);
    }

    public String toSearchQuery() {
        return nameLikeTextField.getText();
    }
}
