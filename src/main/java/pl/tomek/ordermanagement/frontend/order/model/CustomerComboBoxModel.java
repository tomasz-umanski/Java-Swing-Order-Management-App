package pl.tomek.ordermanagement.frontend.order.model;

import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.frontend.commons.DefaultAbstractComboBoxModel;

@Component
public class CustomerComboBoxModel extends DefaultAbstractComboBoxModel<CustomerDto> {
}
