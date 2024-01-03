package pl.tomek.ordermanagement.frontend.customer.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerCreateDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacade;
import pl.tomek.ordermanagement.backend.facade.customer.exception.CustomerCreateDtoValidatorException;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.commons.Notifications;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerTableModel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerButtonPanel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerFrame;
import pl.tomek.ordermanagement.frontend.customer.view.modal.*;

import javax.swing.*;
import java.util.List;

@Controller
public class CustomerFrameController extends AbstractFrameController {
    private final CustomerFrame customerFrame;
    private final CustomerTableModel customerTableModel;
    private final CustomerDialog customerDialog;
    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerFrameController(CustomerFrame customerFrame,
                                   CustomerTableModel customerTableModel,
                                   CustomerFacade customerFacade,
                                   CustomerDialog customerDialog) {
        this.customerFrame = customerFrame;
        this.customerTableModel = customerTableModel;
        this.customerFacade = customerFacade;
        this.customerDialog = customerDialog;
    }

    @PostConstruct
    private void prepare() {
        prepareListeners();
    }

    @Override
    public void initAndOpenFrame() {
        loadEntities();
        customerFrame.setVisible(true);
    }

    private void loadEntities() {
        List<CustomerDto> entities = customerFacade.getAllCustomers();
        customerTableModel.clear();
        customerTableModel.addEntities(entities);
    }

    private void prepareListeners() {
        CustomerButtonPanel customerButtonPanel = customerFrame.customerButtonPanel();
        CustomerAdditionButtonPanel customerAdditionButtonPanel = customerDialog.customerAdditionButtonPanel();

        registerAction(customerButtonPanel.addButton(), e -> showAddModal());
        registerAction(customerButtonPanel.deleteButton(), e -> removeEntity());
        registerAction(customerButtonPanel.detailsButton(), e -> showDetailsModal());

        registerAction(customerAdditionButtonPanel.cancelButton(), e -> hideAddModal());
        registerAction(customerAdditionButtonPanel.saveButton(), e -> saveEntity());
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
            customerDialog.prepareDetailsDialog(customerDto);
            customerDialog.setVisible(true);
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
        } catch (CustomerCreateDtoValidatorException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }
}
