package pl.tomek.ordermanagement.frontend.customer.model;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultTableModel;

@Component
public class CustomerTableModel extends DefaultTableModel<CustomerDto> {
    private static final int NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;
    private static final int COMPANY_NAME_INDEX = 2;
    private static final int TAX_ID_INDEX = 3;
    private static final int HOME_ADDRESS_INDEX = 4;
    private static final int SHIPPING_ADDRESS_INDEX = 5;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Name",
                "Last name",
                "Company Name",
                "Tax id number",
                "Home address",
                "Shipping address"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CustomerDto customerDto = entities.get(rowIndex);

        return switch (columnIndex) {
            case NAME_INDEX -> customerDto.name();
            case LAST_NAME_INDEX -> customerDto.lastName();
            case COMPANY_NAME_INDEX -> customerDto.companyName();
            case TAX_ID_INDEX -> customerDto.taxIdNumber();
            case HOME_ADDRESS_INDEX -> customerDto.homeAddress();
            case SHIPPING_ADDRESS_INDEX -> customerDto.shippingAddress();
            default -> Strings.EMPTY;
        };
    }
}
