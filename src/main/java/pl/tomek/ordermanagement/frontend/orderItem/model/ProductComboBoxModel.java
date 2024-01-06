package pl.tomek.ordermanagement.frontend.orderItem.model;

import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultAbstractComboBoxModel;

@Component
public class ProductComboBoxModel extends DefaultAbstractComboBoxModel<ProductDto> {
}
