package pl.tomek.ordermanagement.frontend.order.view.search;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class OrderSearchButtonPanel extends JPanel {

    private JButton searchButton;

    @PostConstruct
    private void preparePanel() {
        initComponents();
    }

    private void initComponents() {
        searchButton = new JButton("Search");
        add(searchButton);
    }

    public JButton searchButton() {
        return searchButton;
    }
}
