package pl.tomek.ordermanagement.frontend.product.model;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultTableModel;

@Component
public class ProductTableModel extends DefaultTableModel<ProductDto> {
    private static final int NAME_INDEX = 0;
    private static final int DESCRIPTION_INDEX = 1;
    private static final int SKU_INDEX = 2;
    private static final int ESTIMATED_NET_UNIT_PRICE_INDEX = 3;
    private static final int ESTIMATED_GROSS_UNIT_PRICE_INDEX = 4;
    private static final int LENGTH_INDEX = 5;
    private static final int HEIGHT_INDEX = 6;

    private static final int WIDTH_INDEX = 7;

    private static final int WEIGHT_INDEX = 8;

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                "Name",
                "Description",
                "SKU",
                "Estimated Net Unit Price",
                "Estimated Net Gross Price",
                "Length (m)",
                "Height (m)",
                "Width (m)",
                "Weight (kg)"
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductDto productDto = entities.get(rowIndex);
        return switch (columnIndex) {
            case NAME_INDEX -> productDto.name();
            case DESCRIPTION_INDEX -> productDto.description();
            case SKU_INDEX -> productDto.SKU();
            case ESTIMATED_NET_UNIT_PRICE_INDEX -> productDto.estimatedNetUnitPrice();
            case ESTIMATED_GROSS_UNIT_PRICE_INDEX -> productDto.estimatedGrossUnitPrice();
            case LENGTH_INDEX -> productDto.length();
            case HEIGHT_INDEX -> productDto.height();
            case WIDTH_INDEX -> productDto.width();
            case WEIGHT_INDEX -> productDto.weight();
            default -> Strings.EMPTY;
        };
    }
}
