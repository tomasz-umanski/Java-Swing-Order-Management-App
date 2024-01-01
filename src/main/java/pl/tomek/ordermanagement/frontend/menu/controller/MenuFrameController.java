package pl.tomek.ordermanagement.frontend.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.menu.view.MenuFrame;

@Component
public class MenuFrameController extends AbstractFrameController {
    private final MenuFrame menuFrame;

    @Autowired
    public MenuFrameController(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    public void initAndOpenFrame() {
        registerAction(menuFrame.customersManagementButton(), (e -> openCustomerFrame()));
        menuFrame.setVisible(true);
    }

    private void openCustomerFrame() {
    }
}
