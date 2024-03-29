package pl.tomek.ordermanagement.frontend.product.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.product.model.ProductTableModel;

import javax.swing.*;
import java.awt.*;

@Component
public class ProductTablePanel extends JPanel {
    private JTable table;
    private final ProductTableModel productTableModel;

    @Autowired
    public ProductTablePanel(ProductTableModel productTableModel) {
        this.productTableModel = productTableModel;
    }

    @PostConstruct
    private void prepare() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        table = new JTable(productTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable table() {
        return table;
    }
}
