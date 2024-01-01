package pl.tomek.ordermanagement.frontend.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.customer.controller.CustomerFrameController;
import pl.tomek.ordermanagement.frontend.menu.view.MenuFrame;

@Controller
public class MenuFrameController extends AbstractFrameController {
    private final MenuFrame menuFrame;
    private final CustomerFrameController customerFrameController;

    @Autowired
    public MenuFrameController(MenuFrame menuFrame, CustomerFrameController customerFrameController) {
        this.menuFrame = menuFrame;
        this.customerFrameController = customerFrameController;
    }

    public void initAndOpenFrame() {
        registerAction(menuFrame.customersManagementButton(), (e -> openCustomerFrame()));
        menuFrame.setVisible(true);
    }

    private void openCustomerFrame() {
        customerFrameController.initAndOpenFrame();
    }
}
