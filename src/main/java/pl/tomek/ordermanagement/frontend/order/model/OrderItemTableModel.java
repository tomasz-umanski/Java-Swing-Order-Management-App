package pl.tomek.ordermanagement.frontend.order.model;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderItemCreateDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultTableModel;

@Component
public class OrderItemTableModel extends DefaultTableModel<OrderItemCreateDto> {
    private static final int PRODUCT_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;
    private static final int DISCOUNT_INDEX = 2;
    private static final int NET_PRICE_INDEX = 3;
    private static final int GROSS_PRICE_INDEX = 4;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Product",
                "Quantity",
                "Discount",
                "Net Price",
                "Gross Price"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderItemCreateDto orderItemDto = entities.get(rowIndex);
        return switch (columnIndex) {
            case PRODUCT_INDEX -> orderItemDto.product();
            case QUANTITY_INDEX -> orderItemDto.quantity();
            case DISCOUNT_INDEX -> orderItemDto.discount();
            case NET_PRICE_INDEX -> orderItemDto.netPrice();
            case GROSS_PRICE_INDEX -> orderItemDto.grossPrice();
            default -> Strings.EMPTY;
        };
    }
}
