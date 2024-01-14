package pl.tomek.ordermanagement.frontend.customer.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerTableModel;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerTablePanel extends JPanel {
    private JTable table;

    private final CustomerTableModel customerTableModel;

    @Autowired
    public CustomerTablePanel(CustomerTableModel customerTableModel) {
        this.customerTableModel = customerTableModel;
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
        table = new JTable(customerTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable table() {
        return table;
    }
}
