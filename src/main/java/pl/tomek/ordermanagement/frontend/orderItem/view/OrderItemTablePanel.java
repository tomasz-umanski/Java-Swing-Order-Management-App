package pl.tomek.ordermanagement.frontend.orderItem.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.order.model.OrderItemTableModel;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderItemTablePanel extends JPanel {
    private JTable table;
    private final OrderItemTableModel orderItemTableModel;

    @Autowired
    public OrderItemTablePanel(OrderItemTableModel orderItemTableModel) {
        this.orderItemTableModel = orderItemTableModel;
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
        table = new JTable(orderItemTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable table() {
        return table;
    }
}
