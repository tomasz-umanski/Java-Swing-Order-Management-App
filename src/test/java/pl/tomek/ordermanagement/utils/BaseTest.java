package pl.tomek.ordermanagement.utils;

import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tomek.ordermanagement.frontend.customer.controller.CustomerFrameController;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerFrame;
import pl.tomek.ordermanagement.frontend.customer.view.modal.CustomerDialog;
import pl.tomek.ordermanagement.frontend.menu.controller.MenuFrameController;
import pl.tomek.ordermanagement.frontend.menu.view.MenuFrame;
import pl.tomek.ordermanagement.frontend.product.controller.ProductFrameController;
import pl.tomek.ordermanagement.frontend.product.view.ProductFrame;
import pl.tomek.ordermanagement.frontend.product.view.modal.ProductDialog;

public class BaseTest {

    @MockBean
    private MenuFrameController menuFrameController;

    @MockBean
    private MenuFrame menuFrame;

    @MockBean
    private CustomerFrameController customerFrameController;

    @MockBean
    private CustomerFrame customerFrame;

    @MockBean
    private CustomerDialog customerDialog;

    @MockBean
    private ProductFrameController productFrameController;

    @MockBean
    private ProductFrame productFrame;

    @MockBean
    private ProductDialog productDialog;
}
