package pl.tomek.ordermanagement.frontend.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.customer.controller.CustomerFrameController;
import pl.tomek.ordermanagement.frontend.menu.view.MenuFrame;
import pl.tomek.ordermanagement.frontend.product.controller.ProductFrameController;

@Controller
public class MenuFrameController extends AbstractFrameController {
    private final MenuFrame menuFrame;
    private final CustomerFrameController customerFrameController;
    private final ProductFrameController productFrameController;

    @Autowired
    public MenuFrameController(MenuFrame menuFrame, CustomerFrameController customerFrameController, ProductFrameController productFrameController) {
        this.menuFrame = menuFrame;
        this.customerFrameController = customerFrameController;
        this.productFrameController = productFrameController;
    }

    public void initAndOpenFrame() {
        registerAction(menuFrame.customersManagementButton(), (e -> openCustomerFrame()));
        registerAction(menuFrame.productsManagementButton(), (e -> openProductFrame()));

        menuFrame.setVisible(true);
    }

    private void openCustomerFrame() {
        customerFrameController.initAndOpenFrame();
    }

    private void openProductFrame() {
        productFrameController.initAndOpenFrame();
    }
}
