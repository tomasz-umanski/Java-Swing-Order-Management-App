package pl.tomek.ordermanagement.frontend.order.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.order.view.search.OrderSearchPanel;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;
    private final OrderTablePanel orderTablePanel;
    private final OrderButtonPanel orderButtonPanel;
    private final OrderSearchPanel orderSearchPanel;

    @Autowired
    public OrderFrame(OrderTablePanel orderTablePanel, OrderButtonPanel orderButtonPanel, OrderSearchPanel orderSearchPanel) {
        this.orderTablePanel = orderTablePanel;
        this.orderButtonPanel = orderButtonPanel;
        this.orderSearchPanel = orderSearchPanel;
    }

    @PostConstruct
    private void prepare() {
        setFrameUp();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void setFrameUp() {
        setTitle("Order Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
    }

    private void initComponents() {
        add(orderSearchPanel, BorderLayout.NORTH);
        add(orderTablePanel, BorderLayout.CENTER);
        add(orderButtonPanel, BorderLayout.SOUTH);
    }

    public OrderTablePanel orderTablePanel() {
        return orderTablePanel;
    }

    public OrderButtonPanel orderButtonPanel() {
        return orderButtonPanel;
    }

    public OrderSearchPanel orderSearchPanel() {
        return orderSearchPanel;
    }
}
