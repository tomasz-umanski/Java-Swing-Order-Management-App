package pl.tomek.ordermanagement.frontend.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderDto;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderFacade;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.order.model.OrderTableModel;
import pl.tomek.ordermanagement.frontend.order.view.OrderFrame;

import java.util.List;

@Controller
public class OrderFrameController extends AbstractFrameController {
    private final OrderFrame orderFrame;
    private final OrderTableModel orderTableModel;
    private final OrderFacade orderFacade;

    @Autowired
    public OrderFrameController(OrderFrame orderFrame, OrderTableModel orderTableModel, OrderFacade orderFacade) {
        this.orderFrame = orderFrame;
        this.orderTableModel = orderTableModel;
        this.orderFacade = orderFacade;
    }

    @Override
    public void initAndOpenFrame() {
        loadEntities();
        orderFrame.setVisible(true);
    }

    private void loadEntities() {
        List<OrderDto> entities = orderFacade.getAllOrders();
        orderTableModel.clear();
        orderTableModel.addEntities(entities);
    }
}
