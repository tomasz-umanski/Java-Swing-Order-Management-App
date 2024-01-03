package pl.tomek.ordermanagement.frontend.order.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.order.model.OrderTableModel;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderTablePanel extends JPanel {
    private JTable table;
    private final OrderTableModel orderTableModel;

    @Autowired
    public OrderTablePanel(OrderTableModel orderTableModel) {
        this.orderTableModel = orderTableModel;
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
        table = new JTable(orderTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable table() {
        return table;
    }
}
