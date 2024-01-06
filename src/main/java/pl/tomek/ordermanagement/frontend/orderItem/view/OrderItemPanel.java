package pl.tomek.ordermanagement.frontend.orderItem.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderItemPanel extends JPanel {
    private final OrderItemTablePanel orderItemTablePanel;
    private final OrderItemButtonPanel orderItemButtonPanel;

    @Autowired
    public OrderItemPanel(OrderItemTablePanel orderItemTablePanel, OrderItemButtonPanel orderItemButtonPanel) {
        this.orderItemTablePanel = orderItemTablePanel;
        this.orderItemButtonPanel = orderItemButtonPanel;
    }

    @PostConstruct
    private void preparePanel() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        add(orderItemTablePanel, BorderLayout.CENTER);
        add(orderItemButtonPanel, BorderLayout.SOUTH);
    }

    public void prepareAddPanel() {
        orderItemButtonPanel.setVisible(true);
    }

    public void prepareDisplayPanel() {
        orderItemButtonPanel.setVisible(false);
    }

    public OrderItemTablePanel orderItemModalTablePanel() {
        return orderItemTablePanel;
    }

    public OrderItemButtonPanel orderItemButtonPanel() {
        return orderItemButtonPanel;
    }
}
