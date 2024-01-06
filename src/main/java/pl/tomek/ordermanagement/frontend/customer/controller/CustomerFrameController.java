package pl.tomek.ordermanagement.frontend.customer.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.customer.api.*;
import pl.tomek.ordermanagement.backend.facade.customer.exception.CustomerCreateDtoValidatorException;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.commons.Notifications;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerOrderTableModel;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerTableModel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerButtonPanel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerFrame;
import pl.tomek.ordermanagement.frontend.customer.view.modal.*;
import pl.tomek.ordermanagement.frontend.customer.view.search.CustomerSearchButtonPanel;
import pl.tomek.ordermanagement.frontend.customer.view.search.CustomerSearchQuery;
import pl.tomek.ordermanagement.frontend.customer.view.search.CustomerSearchQueryPanel;

import javax.swing.*;
import java.util.List;

@Controller
public class CustomerFrameController extends AbstractFrameController {
    private final CustomerFrame customerFrame;
    private final CustomerTableModel customerTableModel;
    private final CustomerOrderTableModel customerOrderTableModel;
    private final CustomerDialog customerDialog;
    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerFrameController(CustomerFrame customerFrame,
                                   CustomerTableModel customerTableModel, CustomerOrderTableModel customerOrderTableModel,
                                   CustomerFacade customerFacade,
                                   CustomerDialog customerDialog) {
        this.customerFrame = customerFrame;
        this.customerTableModel = customerTableModel;
        this.customerOrderTableModel = customerOrderTableModel;
        this.customerFacade = customerFacade;
        this.customerDialog = customerDialog;
    }

    @PostConstruct
    private void prepare() {
        prepareListeners();
    }

    @Override
    public void initAndOpenFrame() {
        loadCustomers();
        customerFrame.setVisible(true);
    }

    private void loadCustomers() {
        List<CustomerDto> entities = customerFacade.getAllCustomers();
        customerTableModel.clear();
        customerTableModel.addEntities(entities);
    }

    private void prepareListeners() {
        CustomerButtonPanel customerButtonPanel = customerFrame.customerButtonPanel();
        CustomerAdditionButtonPanel customerAdditionButtonPanel = customerDialog.customerAdditionButtonPanel();
        CustomerSearchButtonPanel customerSearchButtonPanel = customerFrame.customerSearchPanel().customerSearchButtonPanel();

        registerAction(customerButtonPanel.addButton(), e -> showAddModal());
        registerAction(customerButtonPanel.deleteButton(), e -> removeEntity());
        registerAction(customerButtonPanel.detailsButton(), e -> showDetailsModal());

        registerAction(customerAdditionButtonPanel.cancelButton(), e -> hideAddModal());
        registerAction(customerAdditionButtonPanel.saveButton(), e -> saveEntity());

        registerAction(customerSearchButtonPanel.searchButton(), e -> searchByQuery());
    }

    private void searchByQuery() {
        CustomerSearchQueryPanel customerSearchQueryPanel = customerFrame.customerSearchPanel().customerSearchQueryPanel();
        try {
            CustomerSearchQuery customerSearchQuery = customerSearchQueryPanel.toSearchQuery();
            List<CustomerDto> entities = customerFacade.getCustomersWithFilteredOrders(
                    customerSearchQuery.startDate(),
                    customerSearchQuery.endDate(),
                    customerSearchQuery.fromValue()
            );
            customerTableModel.clear();
            customerTableModel.addEntities(entities);
        } catch (CustomerCreateDtoValidatorException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }

    private void showAddModal() {
        customerDialog.prepareAddDialog();
        customerDialog.setVisible(true);
    }

    private void removeEntity() {
        try {
            JTable customerTable = customerFrame.customerTablePanel().table();
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "No row selected",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                CustomerDto entity = customerTableModel.getEntityByRow(selectedRow);
                customerFacade.deleteCustomer(entity);
                customerTableModel.removeRow(selectedRow);
            }
        } catch (Exception e) {
            Notifications.showDeleteRowErrorMessage();
        }
    }

    private void showDetailsModal() {
        JTable customerTable = customerFrame.customerTablePanel().table();
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No row selected",
                    "Alert",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            CustomerDto customerDto = customerTableModel.getEntityByRow(selectedRow);
            loadOrdersForCustomer(customerDto.customerOrderDtoList());
            customerDialog.prepareDetailsDialog(customerDto);
            customerDialog.setVisible(true);
        }
    }

    private void loadOrdersForCustomer(List<CustomerOrderDto> customerOrderDtoList) {
        customerOrderTableModel.clear();
        if (customerOrderDtoList != null) {
            customerOrderTableModel.addEntities(customerOrderDtoList);
        }
    }

    private void hideAddModal() {
        customerDialog.customerAdditionFormPanel().clearForm();
        customerDialog.homeAddressAdditionFormPanel().clearForm();
        customerDialog.shippingAddressAdditionFormPanel().clearForm(true);
        customerDialog.dispose();
    }

    private void saveEntity() {
        CustomerFormPanel customerFormPanel = customerDialog.customerAdditionFormPanel();
        HomeAddressFormPanel homeAddressFormPanel = customerDialog.homeAddressAdditionFormPanel();
        ShippingAddressFormPanel shippingAddressFormPanel = customerDialog.shippingAddressAdditionFormPanel();
        JCheckBox sameAsHomeAddressCheckbox = shippingAddressFormPanel.sameAsHomeAddressCheckbox();

        AddressCreateDto homeAddressCreateDto = homeAddressFormPanel.toCreateDto();
        AddressCreateDto shippingAddressCreateDto = null;
        if (sameAsHomeAddressCheckbox.isSelected()) {
            shippingAddressCreateDto = homeAddressCreateDto;
        } else if (sameAsHomeAddressCheckbox.isVisible() && !sameAsHomeAddressCheckbox.isSelected()) {
            shippingAddressCreateDto = shippingAddressFormPanel.toCreateDto();
        }
        CustomerCreateDto customerCreateDto = customerFormPanel.toCreateDto(homeAddressCreateDto, shippingAddressCreateDto);
        try {
            CustomerDto createdCustomerDto = customerFacade.saveCustomer(customerCreateDto);
            customerTableModel.addEntity(createdCustomerDto);
            sameAsHomeAddressCheckbox.setVisible(false);
            hideAddModal();
        } catch (CustomerCreateDtoValidatorException | DataIntegrityViolationException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }
}
