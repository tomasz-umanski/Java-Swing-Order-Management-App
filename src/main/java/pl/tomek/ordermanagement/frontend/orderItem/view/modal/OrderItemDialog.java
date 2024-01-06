package pl.tomek.ordermanagement.frontend.orderItem.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderItemDialog extends JFrame {
    private final OrderItemModalFormPanel orderItemModalFormPanel;
    private final OrderItemModalButtonPanel orderItemModalButtonPanel;

    @Autowired
    public OrderItemDialog(OrderItemModalFormPanel orderItemModalFormPanel, OrderItemModalButtonPanel orderItemModalButtonPanel) {
        this.orderItemModalFormPanel = orderItemModalFormPanel;
        this.orderItemModalButtonPanel = orderItemModalButtonPanel;
    }

    @PostConstruct
    private void prepare() {
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("Order Item");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        add(orderItemModalFormPanel, BorderLayout.CENTER);
        add(orderItemModalButtonPanel, BorderLayout.SOUTH);
    }

    public void prepareAddDialog() {
        prepareAddComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void prepareAddComponents() {
        orderItemModalFormPanel.prepareAddPanel();
        orderItemModalButtonPanel.setVisible(true);
    }

    public OrderItemModalFormPanel orderItemModalFormPanel() {
        return orderItemModalFormPanel;
    }

    public OrderItemModalButtonPanel orderItemModalButtonPanel() {
        return orderItemModalButtonPanel;
    }
}
