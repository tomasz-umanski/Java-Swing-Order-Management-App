package pl.tomek.ordermanagement.frontend.customer.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.customer.api.AddressDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacade;
import pl.tomek.ordermanagement.backend.feature.validation.ObjectsValidator;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.commons.Notifications;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerTableModel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerButtonPanel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerFrame;
import pl.tomek.ordermanagement.frontend.customer.view.modal.*;

import javax.swing.*;
import java.util.List;
import java.util.Set;

@Controller
public class CustomerFrameController extends AbstractFrameController {
    private final CustomerFrame customerFrame;
    private final CustomerTableModel customerTableModel;
    private final CustomerAdditionDialog customerAdditionDialog;
    private final CustomerFacade customerFacade;
    private final ObjectsValidator<CustomerDto> validator;

    @Autowired
    public CustomerFrameController(CustomerFrame customerFrame, CustomerTableModel customerTableModel, CustomerFacade customerFacade, CustomerAdditionDialog customerAdditionDialog, ObjectsValidator<CustomerDto> validator) {
        this.customerFrame = customerFrame;
        this.customerTableModel = customerTableModel;
        this.customerFacade = customerFacade;
        this.customerAdditionDialog = customerAdditionDialog;
        this.validator = validator;
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
        CustomerAdditionButtonPanel customerAdditionButtonPanel = customerAdditionDialog.customerAdditionButtonPanel();

        registerAction(customerButtonPanel.addButton(), e -> showAddModal());
        registerAction(customerButtonPanel.deleteButton(), e -> removeEntity());

        registerAction(customerAdditionButtonPanel.cancelButton(), e -> hideAddModal());
        registerAction(customerAdditionButtonPanel.saveButton(), e -> saveEntity());
    }

    private void showAddModal() {
        customerAdditionDialog.setVisible(true);
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

    private void hideAddModal() {
        customerAdditionDialog.customerAdditionFormPanel().clearForm();
        customerAdditionDialog.homeAddressAdditionFormPanel().clearForm();
        customerAdditionDialog.shippingAddressAdditionFormPanel().clearForm(true);
        customerAdditionDialog.dispose();
    }

    private void saveEntity() {
        CustomerAdditionFormPanel customerAdditionFormPanel = customerAdditionDialog.customerAdditionFormPanel();
        HomeAddressAdditionFormPanel homeAddressAdditionFormPanel = customerAdditionDialog.homeAddressAdditionFormPanel();
        ShippingAddressAdditionFormPanel shippingAddressAdditionFormPanel = customerAdditionDialog.shippingAddressAdditionFormPanel();
        JCheckBox sameAsHomeAddressCheckbox = shippingAddressAdditionFormPanel.sameAsHomeAddressCheckbox();

        AddressDto homeAddressDto = homeAddressAdditionFormPanel.toDto();
        AddressDto shippingAddressDto = null;
        if (sameAsHomeAddressCheckbox.isSelected()) {
            shippingAddressDto = homeAddressDto;
        } else if (sameAsHomeAddressCheckbox.isVisible() && !sameAsHomeAddressCheckbox.isSelected()) {
            shippingAddressDto = shippingAddressAdditionFormPanel.toDto();
        }
        CustomerDto customerDto = customerAdditionFormPanel.toDto(homeAddressDto, shippingAddressDto);
        Set<String> violations = validator.validate(customerDto);
        if (!violations.isEmpty()) {
            Notifications.showFormValidationAlert(String.join(" | ", violations));
        } else {
            customerDto = customerFacade.saveCustomer(customerDto);
            customerTableModel.addEntity(customerDto);
            sameAsHomeAddressCheckbox.setVisible(false);
            hideAddModal();
        }
    }
}
