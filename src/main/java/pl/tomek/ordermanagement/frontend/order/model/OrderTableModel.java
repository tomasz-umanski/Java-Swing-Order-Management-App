package pl.tomek.ordermanagement.frontend.order.model;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultTableModel;

@Component
public class OrderTableModel extends DefaultTableModel<OrderDto> {
    private static final int ID_INDEX = 0;
    private static final int ORDER_DATE_INDEX = 1;
    private static final int CUSTOMER_INDEX = 2;
    private static final int SHIPPING_ADDRESS_INDEX = 3;
    private static final int ORDER_VALUE_INDEX = 4;


    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Id",
                "Order Date",
                "Customer",
                "Shipping Address",
                "Order Value"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderDto orderDto = entities.get(rowIndex);
        return switch (columnIndex) {
            case ID_INDEX -> orderDto.id();
            case ORDER_DATE_INDEX -> orderDto.orderDate();
            case CUSTOMER_INDEX -> orderDto.customer();
            case SHIPPING_ADDRESS_INDEX -> orderDto.shippingAddress();
            case ORDER_VALUE_INDEX -> orderDto.orderValue();
            default -> Strings.EMPTY;
        };
    }
}
