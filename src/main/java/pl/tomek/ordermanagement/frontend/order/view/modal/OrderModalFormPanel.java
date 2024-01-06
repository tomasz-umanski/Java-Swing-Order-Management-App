package pl.tomek.ordermanagement.frontend.order.view.modal;

import com.toedter.calendar.JDateChooser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderCreateDto;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderDto;
import pl.tomek.ordermanagement.backend.facade.order.api.OrderItemCreateDto;
import pl.tomek.ordermanagement.frontend.commons.Borders;
import pl.tomek.ordermanagement.frontend.order.model.AddressComboBoxModel;
import pl.tomek.ordermanagement.frontend.order.model.CustomerComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class OrderModalFormPanel extends JPanel {
    private static final int LAYOUT_ROWS = 10;
    private static final int LAYOUT_COLS = 2;
    private static final int HORIZONTAL_GAP = 0;
    private static final int VERTICAL_GAP = 20;
    private final JLabel orderDateLabel = new JLabel("Order Date");
    private JDateChooser orderDateChooser;
    private final JLabel customerLabel = new JLabel("Customer");
    private JComboBox<CustomerDto> customerComboBox;
    private final CustomerComboBoxModel customerComboBoxModel;
    private final JLabel addressLabel = new JLabel("Address");
    private JComboBox<AddressDto> addressComboBox;
    private final AddressComboBoxModel addressComboBoxModel;

    @Autowired
    public OrderModalFormPanel(CustomerComboBoxModel customerComboBoxModel, AddressComboBoxModel addressComboBoxModel) {
        this.customerComboBoxModel = customerComboBoxModel;
        this.addressComboBoxModel = addressComboBoxModel;
    }

    @PostConstruct
    private void preparePanel() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    private void initComponents() {
        orderDateChooser = new JDateChooser();
        customerComboBox = new JComboBox<>(customerComboBoxModel);
        addressComboBox = new JComboBox<>(addressComboBoxModel);

        add(orderDateLabel);
        add(orderDateChooser, BorderLayout.CENTER);
        add(customerLabel);
        add(customerComboBox);
        add(addressLabel);
        add(addressComboBox);
    }

    public void prepareAddPanel() {
        initAddComponents();
    }

    private void initAddComponents() {
        setFieldsEnabled(true);
        clearForm();
    }

    public void prepareDetailsPanel(OrderDto orderDto) {
        initDetailsComponents(orderDto);
    }

    private void initDetailsComponents(OrderDto orderDto) {
        setFieldsEnabled(false);
        setData(orderDto);
    }

    private void setFieldsEnabled(boolean enabled) {
        orderDateChooser.setEnabled(enabled);
        customerComboBox.setEnabled(enabled);
        addressComboBox.setEnabled(enabled);
    }

    private void setData(OrderDto orderDto) {
        orderDateChooser.setDate(convertToDate(orderDto.orderDate()));
        customerComboBoxModel.clear();
        customerComboBoxModel.addElement(orderDto.customer());
        addressComboBoxModel.clear();
        addressComboBoxModel.addElement(orderDto.shippingAddress());
    }

    public OrderCreateDto toCreateDto(List<OrderItemCreateDto> orderItemCreateDtoList) {
        int selectedIndex = customerComboBox.getSelectedIndex();
        CustomerDto customerDto = customerComboBox.getItemAt(selectedIndex);
        selectedIndex = addressComboBox.getSelectedIndex();
        AddressDto addressDto = addressComboBox.getItemAt(selectedIndex);

        return new OrderCreateDto(
                convertToLocalDate(orderDateChooser.getDate()),
                customerDto,
                addressDto,
                orderItemCreateDtoList
        );
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        if (dateToConvert != null) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null;
    }

    public Date convertToDate(LocalDate dateToConvert) {
        if (dateToConvert != null) {
            return Date.from(dateToConvert.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }
        return null;
    }

    public void clearForm() {
        orderDateChooser.setDate(null);
    }

    public JComboBox<CustomerDto> customerComboBox() {
        return customerComboBox;
    }
}
