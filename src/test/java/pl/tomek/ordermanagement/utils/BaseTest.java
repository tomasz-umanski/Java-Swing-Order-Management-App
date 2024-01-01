package pl.tomek.ordermanagement.utils;

import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tomek.ordermanagement.frontend.menu.controller.MenuFrameController;
import pl.tomek.ordermanagement.frontend.menu.view.MenuFrame;

public class BaseTest {

    @MockBean
    private MenuFrameController menuFrameController;

    @MockBean
    private MenuFrame menuFrame;
}
