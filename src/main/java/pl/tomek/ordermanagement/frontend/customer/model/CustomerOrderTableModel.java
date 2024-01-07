package pl.tomek.ordermanagement.frontend.customer.model;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerOrderDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultTableModel;

@Component
public class CustomerOrderTableModel extends DefaultTableModel<CustomerOrderDto> {
    private static final int ORDER_ID_INDEX = 0;
    private static final int ORDER_DATE_INDEX = 1;
    private static final int ORDER_VALUE_INDEX = 2;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Order Id",
                "Order Date",
                "Order Value"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CustomerOrderDto customerOrderDto = entities.get(rowIndex);
        return switch (columnIndex) {
            case ORDER_ID_INDEX -> customerOrderDto.id();
            case ORDER_DATE_INDEX -> customerOrderDto.orderDate();
            case ORDER_VALUE_INDEX -> customerOrderDto.orderValue();
            default -> Strings.EMPTY;
        };
    }
}
