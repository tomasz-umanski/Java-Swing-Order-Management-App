package pl.tomek.ordermanagement.frontend.commons;

import jakarta.annotation.PostConstruct;

import javax.swing.*;

public class DefaultButtonPanel extends JPanel {
    private JButton addButton;
    private JButton deleteButton;
    private JButton detailsButton;

    @PostConstruct
    private void prepare() {
        initComponents();
    }

    private void initComponents() {
        addButton = new JButton("Add");
        add(addButton);

        deleteButton = new JButton("Delete");
        add(deleteButton);

        detailsButton = new JButton("Show Details");
        add(detailsButton);
    }

    public JButton addButton() {
        return addButton;
    }

    public JButton deleteButton() {
        return deleteButton;
    }

    public JButton detailsButton() {
        return detailsButton;
    }
}
