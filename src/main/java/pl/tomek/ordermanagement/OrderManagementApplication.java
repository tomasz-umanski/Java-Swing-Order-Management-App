package pl.tomek.ordermanagement;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.tomek.ordermanagement.frontend.menu.controller.MenuFrameController;

import javax.swing.*;

@SpringBootApplication
public class OrderManagementApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = createApplicationContext(args);
        displayMainFrame(context);
    }

    private static ConfigurableApplicationContext createApplicationContext(String... args) {
        return new SpringApplicationBuilder(OrderManagementApplication.class)
                .headless(false)
                .run(args);
    }

    private static void displayMainFrame(ConfigurableApplicationContext context) {
        SwingUtilities.invokeLater(() -> {
            MenuFrameController menuFrame = context.getBean(MenuFrameController.class);
            menuFrame.initAndOpenFrame();
        });
    }
}
