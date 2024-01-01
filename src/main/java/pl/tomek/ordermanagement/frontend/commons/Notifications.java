package pl.tomek.ordermanagement.frontend.commons;

import javax.swing.*;

public class Notifications {

    public static void showFormValidationAlert(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showDeleteRowErrorMessage() {
        JOptionPane.showMessageDialog(
                null,
                "Could not delete a row",
                "Alert",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
