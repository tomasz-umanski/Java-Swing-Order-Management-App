package pl.tomek.ordermanagement.frontend.order.view.modal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderDto;
import pl.tomek.ordermanagement.frontend.orderItem.view.OrderItemPanel;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderDialog extends JFrame {
    private final OrderModalFormPanel orderModalFormPanel;
    private final OrderModalButtonPanel orderModalButtonPanel;
    private final OrderItemPanel orderItemPanel;

    @Autowired
    public OrderDialog(OrderModalFormPanel orderModalFormPanel, OrderModalButtonPanel orderModalButtonPanel, OrderItemPanel orderItemPanel) {
        this.orderModalFormPanel = orderModalFormPanel;
        this.orderModalButtonPanel = orderModalButtonPanel;
        this.orderItemPanel = orderItemPanel;
    }

    @PostConstruct
    private void prepareFrame() {
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("Order");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
    }

    private void initComponents() {
        add(orderModalFormPanel, BorderLayout.WEST);
        add(orderItemPanel, BorderLayout.EAST);
        add(orderModalButtonPanel, BorderLayout.SOUTH);
    }

    public void prepareAddDialog() {
        prepareAddComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void prepareAddComponents() {
        orderModalFormPanel.prepareAddPanel();
        orderItemPanel.prepareAddPanel();
        orderModalButtonPanel.setVisible(true);
    }

    public void prepareDetailsDialog(OrderDto orderDto) {
        prepareDetailsComponents(orderDto);
        pack();
        setLocationRelativeTo(null);
    }

    private void prepareDetailsComponents(OrderDto orderDto) {
        orderModalFormPanel.prepareDetailsPanel(orderDto);
        orderItemPanel.prepareDisplayPanel();
        orderModalButtonPanel.setVisible(false);
    }

    public OrderModalFormPanel orderModalFormPanel() {
        return orderModalFormPanel;
    }

    public OrderModalButtonPanel orderModalButtonPanel() {
        return orderModalButtonPanel;
    }

    public OrderItemPanel orderItemFormPanel() {
        return orderItemPanel;
    }
}
