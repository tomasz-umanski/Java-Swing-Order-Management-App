package pl.tomek.ordermanagement.utils;

import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tomek.ordermanagement.frontend.customer.controller.CustomerFrameController;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerButtonPanel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerFrame;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerTablePanel;
import pl.tomek.ordermanagement.frontend.customer.view.modal.CustomerAdditionDialog;
import pl.tomek.ordermanagement.frontend.customer.view.modal.CustomerAdditionFormPanel;
import pl.tomek.ordermanagement.frontend.menu.controller.MenuFrameController;
import pl.tomek.ordermanagement.frontend.menu.view.MenuFrame;

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
    private CustomerAdditionDialog customerAdditionDialog;
}
