package pl.tomek.ordermanagement.frontend.commons;

import jakarta.annotation.PostConstruct;

import javax.swing.*;

public abstract class DefaultButtonPanel extends JPanel {
    protected JButton addButton;
    protected JButton deleteButton;
    protected JButton detailsButton;

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
