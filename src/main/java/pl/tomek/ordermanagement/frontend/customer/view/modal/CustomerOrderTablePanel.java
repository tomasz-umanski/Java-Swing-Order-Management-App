package pl.tomek.ordermanagement.frontend.customer.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerOrderTableModel;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerOrderTablePanel extends JPanel {
    private JTable table;
    private final CustomerOrderTableModel customerOrderTableModel;

    @Autowired
    public CustomerOrderTablePanel(CustomerOrderTableModel customerOrderTableModel) {
        this.customerOrderTableModel = customerOrderTableModel;
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
        table = new JTable(customerOrderTableModel);
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
