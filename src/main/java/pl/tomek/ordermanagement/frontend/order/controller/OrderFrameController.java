package pl.tomek.ordermanagement.frontend.order.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacade;
import pl.tomek.ordermanagement.backend.facade.customer.exception.CustomerCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.facade.order.api.*;
import pl.tomek.ordermanagement.backend.facade.order.exception.OrderCreateDtoValidatorException;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductFacade;
import pl.tomek.ordermanagement.backend.validation.ObjectsValidator;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.commons.Notifications;
import pl.tomek.ordermanagement.frontend.order.model.AddressComboBoxModel;
import pl.tomek.ordermanagement.frontend.order.model.CustomerComboBoxModel;
import pl.tomek.ordermanagement.frontend.order.model.OrderItemTableModel;
import pl.tomek.ordermanagement.frontend.order.model.OrderTableModel;
import pl.tomek.ordermanagement.frontend.order.view.OrderButtonPanel;
import pl.tomek.ordermanagement.frontend.order.view.OrderFrame;
import pl.tomek.ordermanagement.frontend.order.view.modal.OrderDialog;
import pl.tomek.ordermanagement.frontend.order.view.modal.OrderModalButtonPanel;
import pl.tomek.ordermanagement.frontend.order.view.modal.OrderModalFormPanel;
import pl.tomek.ordermanagement.frontend.order.view.search.OrderSearchButtonPanel;
import pl.tomek.ordermanagement.frontend.order.view.search.OrderSearchQuery;
import pl.tomek.ordermanagement.frontend.order.view.search.OrderSearchQueryPanel;
import pl.tomek.ordermanagement.frontend.orderItem.model.ProductComboBoxModel;
import pl.tomek.ordermanagement.frontend.orderItem.view.OrderItemButtonPanel;
import pl.tomek.ordermanagement.frontend.orderItem.view.OrderItemPanel;
import pl.tomek.ordermanagement.frontend.orderItem.view.modal.OrderItemDialog;
import pl.tomek.ordermanagement.frontend.orderItem.view.modal.OrderItemModalButtonPanel;
import pl.tomek.ordermanagement.frontend.orderItem.view.modal.OrderItemModalFormPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class OrderFrameController extends AbstractFrameController {
    private final OrderItemDialog orderItemDialog;
    private final OrderFrame orderFrame;
    private final OrderItemPanel orderItemPanel;
    private final OrderDialog orderDialog;
    private final OrderTableModel orderTableModel;
    private final OrderItemTableModel orderItemTableModel;
    private final CustomerComboBoxModel customerComboBoxModel;
    private final AddressComboBoxModel addressComboBoxModel;
    private final ProductComboBoxModel productComboBoxModel;
    private final OrderFacade orderFacade;
    private final CustomerFacade customerFacade;
    private final ProductFacade productFacade;
    private final ObjectsValidator<OrderItemCreateDto> validator;

    @Autowired
    public OrderFrameController(OrderItemDialog orderItemDialog,
                                OrderFrame orderFrame,
                                OrderItemPanel orderItemPanel,
                                OrderDialog orderDialog,
                                OrderTableModel orderTableModel,
                                CustomerComboBoxModel customerComboBoxModel,
                                AddressComboBoxModel addressComboBoxModel,
                                OrderFacade orderFacade,
                                CustomerFacade customerFacade,
                                OrderItemTableModel orderItemTableModel,
                                ProductComboBoxModel productComboBoxModel,
                                ProductFacade productFacade,
                                ObjectsValidator<OrderItemCreateDto> validator) {
        this.orderItemDialog = orderItemDialog;
        this.orderFrame = orderFrame;
        this.orderItemPanel = orderItemPanel;
        this.orderDialog = orderDialog;
        this.orderTableModel = orderTableModel;
        this.customerComboBoxModel = customerComboBoxModel;
        this.addressComboBoxModel = addressComboBoxModel;
        this.orderFacade = orderFacade;
        this.customerFacade = customerFacade;
        this.orderItemTableModel = orderItemTableModel;
        this.productComboBoxModel = productComboBoxModel;
        this.productFacade = productFacade;
        this.validator = validator;
    }

    @PostConstruct
    private void prepare() {
        prepareListeners();
    }

    private void prepareListeners() {
        OrderButtonPanel orderButtonPanel = orderFrame.orderButtonPanel();
        OrderModalFormPanel orderModalFormPanel = orderDialog.orderModalFormPanel();
        OrderModalButtonPanel orderModalButtonPanel = orderDialog.orderModalButtonPanel();
        OrderItemButtonPanel orderItemButtonPanel = orderDialog.orderItemFormPanel().orderItemButtonPanel();
        OrderItemModalButtonPanel orderItemModalButtonPanel = orderItemDialog.orderItemModalButtonPanel();
        OrderSearchButtonPanel orderSearchButtonPanel = orderFrame.orderSearchPanel().orderSearchButtonPanel();

        registerAction(orderButtonPanel.addButton(), e -> showAddOrderModal());
        registerAction(orderButtonPanel.deleteButton(), e -> removeOrder());
        registerAction(orderButtonPanel.detailsButton(), e -> showOrderDetailsModal());

        registerAction(orderModalButtonPanel.saveButton(), e -> saveOrder());
        registerAction(orderModalButtonPanel.cancelButton(), e -> hideAddOrderModal());

        registerAction(orderItemButtonPanel.addButton(), e -> showAddOrderItemModal());
        registerAction(orderItemButtonPanel.removeButton(), e -> removeOrderItem());

        registerAction(orderItemModalButtonPanel.addButton(), e -> addOrderItemEntity());
        registerAction(orderItemModalButtonPanel.cancelButton(), e -> closeOrderItemModal());

        orderModalFormPanel.customerComboBox().addActionListener(e -> loadAddresses());

        registerAction(orderSearchButtonPanel.searchButton(), e -> searchByQuery());
        registerAction(orderSearchButtonPanel.clearButton(), e -> clearSearchQueries());

    }

    private void clearSearchQueries() {
        orderFrame.orderSearchPanel().orderSearchQueryPanel().clearForm();
    }

    private void searchByQuery() {
        OrderSearchQueryPanel orderSearchQueryPanel = orderFrame.orderSearchPanel().orderSearchQueryPanel();
        try {
            OrderSearchQuery orderSearchQuery = orderSearchQueryPanel.toSearchQuery();
            List<OrderDto> entities = orderFacade.getFilteredOrders(
                    orderSearchQuery.startDate(),
                    orderSearchQuery.endDate(),
                    orderSearchQuery.fromValue(),
                    orderSearchQuery.toValue(),
                    orderSearchQuery.customerId()
            );
            orderTableModel.clear();
            orderTableModel.addEntities(entities);
        } catch (CustomerCreateDtoValidatorException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }

    private void showAddOrderItemModal() {
        loadProducts();
        orderItemDialog.prepareAddDialog();
        orderItemDialog.orderItemModalFormPanel().putProposedValuesFromProduct();
        orderItemDialog.setVisible(true);
    }

    private void loadProducts() {
        List<ProductDto> products = productFacade.getAllProducts();
        productComboBoxModel.clear();
        productComboBoxModel.addElements(products);
    }

    private void removeOrderItem() {
        try {
            JTable orderItemTable = orderItemPanel.orderItemModalTablePanel().table();
            int selectedRow = orderItemTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "No row selected",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                orderItemTableModel.removeRow(selectedRow);
            }
        } catch (Exception e) {
            Notifications.showDeleteRowErrorMessage();
        }
    }

    private void showAddOrderModal() {
        orderItemTableModel.clear();
        orderDialog.prepareAddDialog();
        orderDialog.setVisible(true);
    }

    private void loadCustomers() {
        List<CustomerDto> customers = customerFacade.getAllCustomers();
        customerComboBoxModel.clear();
        customerComboBoxModel.addElement(null);
        customerComboBoxModel.addElements(customers);
    }

    private void loadAddresses() {
        CustomerDto customerDto = customerComboBoxModel.getSelectedItem();
        addressComboBoxModel.clear();
        if (customerDto != null) {
            List<AddressDto> addresses = customerFacade.getCustomersAddresses(customerDto.id());
            addressComboBoxModel.addElements(addresses);
            orderDialog.pack();
        }
    }

    private void removeOrder() {
        try {
            JTable productTable = orderFrame.orderTablePanel().table();
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "No row selected",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                OrderDto entity = orderTableModel.getEntityByRow(selectedRow);
                orderFacade.deleteOrder(entity);
                orderTableModel.removeRow(selectedRow);
            }
        } catch (Exception e) {
            Notifications.showDeleteRowErrorMessage();
        }
    }

    private void showOrderDetailsModal() {
        JTable productTable = orderFrame.orderTablePanel().table();
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No row selected",
                    "Alert",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            OrderDto orderDto = orderTableModel.getEntityByRow(selectedRow);
            loadOrderItems(orderDto.orderItems());
            orderDialog.prepareDetailsDialog(orderDto);
            orderDialog.setVisible(true);
        }
    }

    private void loadOrderItems(Set<OrderItemDto> orderItemDtoSet) {
        List<OrderItemCreateDto> orderItemCreateDtoList = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoSet) {
            orderItemCreateDtoList.add(orderItemDto.toCreateDto());
        }
        orderItemTableModel.clear();
        orderItemTableModel.addEntities(orderItemCreateDtoList);
    }

    private void saveOrder() {
        OrderModalFormPanel orderModalFormPanel = orderDialog.orderModalFormPanel();
        try {
            JTable orderItemTable = orderDialog.orderItemFormPanel().orderItemModalTablePanel().table();
            List<OrderItemCreateDto> orderItemCreateDtoList = new ArrayList<>();
            int tableSize = orderItemTable.getRowCount();
            for (int i = 0; i < tableSize; i++) {
                orderItemCreateDtoList.add(orderItemTableModel.getEntityByRow(i));
            }
            OrderCreateDto orderCreateDto = orderModalFormPanel.toCreateDto(orderItemCreateDtoList);
            OrderDto createdOrderDto = orderFacade.saveOrder(orderCreateDto);
            orderTableModel.addEntity(createdOrderDto);
            hideAddOrderModal();
        } catch (OrderCreateDtoValidatorException | DataIntegrityViolationException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }

    private void hideAddOrderModal() {
        orderDialog.orderModalFormPanel().clearForm();
        orderItemTableModel.clear();
        orderDialog.dispose();
    }

    @Override
    public void initAndOpenFrame() {
        loadOrders();
        loadCustomers();
        orderFrame.setVisible(true);
    }

    private void loadOrders() {
        List<OrderDto> entities = orderFacade.getAllOrders();
        orderTableModel.clear();
        orderTableModel.addEntities(entities);
    }

    private void addOrderItemEntity() {
        OrderItemModalFormPanel orderItemModalFormPanel = orderItemDialog.orderItemModalFormPanel();
        try {
            OrderItemCreateDto orderItemCreateDto = orderItemModalFormPanel.toCreateDto();
            Set<String> violations = validator.validate(orderItemCreateDto);
            if (!violations.isEmpty()) {
                throw new OrderCreateDtoValidatorException(violations);
            }
            orderItemTableModel.addEntity(orderItemCreateDto);
            closeOrderItemModal();
        } catch (OrderCreateDtoValidatorException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }

    private void closeOrderItemModal() {
        orderItemDialog.orderItemModalFormPanel().clearForm();
        orderItemDialog.dispose();
    }
}
