package pl.tomek.ordermanagement.frontend.order.view.search;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.Borders;

import javax.swing.*;
import java.awt.*;

@Component
public class OrderSearchPanel extends JPanel {
    private final OrderSearchQueryPanel orderSearchQueryPanel;
    private final OrderSearchButtonPanel orderSearchButtonPanel;

    @Autowired
    public OrderSearchPanel(OrderSearchQueryPanel orderSearchQueryPanel, OrderSearchButtonPanel orderSearchButtonPanel) {
        this.orderSearchQueryPanel = orderSearchQueryPanel;
        this.orderSearchButtonPanel = orderSearchButtonPanel;
    }


    @PostConstruct
    private void prepare() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        add(orderSearchQueryPanel, BorderLayout.NORTH);
        add(orderSearchButtonPanel, BorderLayout.SOUTH);
    }

    public OrderSearchQueryPanel orderSearchQueryPanel() {
        return orderSearchQueryPanel;
    }

    public OrderSearchButtonPanel orderSearchButtonPanel() {
        return orderSearchButtonPanel;
    }
}
