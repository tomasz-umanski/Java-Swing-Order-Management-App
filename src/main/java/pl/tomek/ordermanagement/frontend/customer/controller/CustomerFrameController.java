package pl.tomek.ordermanagement.frontend.customer.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerDto;
import pl.tomek.ordermanagement.backend.facade.customer.api.CustomerFacade;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.commons.Notifications;
import pl.tomek.ordermanagement.frontend.customer.model.CustomerTableModel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerButtonPanel;
import pl.tomek.ordermanagement.frontend.customer.view.CustomerFrame;
import pl.tomek.ordermanagement.frontend.customer.view.modal.CustomerAdditionDialog;

import javax.swing.*;
import java.util.List;

@Controller
public class CustomerFrameController extends AbstractFrameController {
    private final CustomerFrame customerFrame;
    private final CustomerTableModel customerTableModel;
    private final CustomerFacade customerFacade;
    private final CustomerAdditionDialog customerAdditionDialog;

    @Autowired
    public CustomerFrameController(CustomerFrame customerFrame, CustomerTableModel customerTableModel, CustomerFacade customerFacade, CustomerAdditionDialog customerAdditionDialog) {
        this.customerFrame = customerFrame;
        this.customerTableModel = customerTableModel;
        this.customerFacade = customerFacade;
        this.customerAdditionDialog = customerAdditionDialog;
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

    private void prepareListeners() {
        CustomerButtonPanel customerButtonPanel = customerFrame.customerButtonPanel();

        registerAction(customerButtonPanel.addButton(), e -> showAddModal());
        registerAction(customerButtonPanel.deleteButton(), e -> removeEntity());
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

    private void loadEntities() {
        List<CustomerDto> entities = customerFacade.getAllCustomers();
        customerTableModel.clear();
        customerTableModel.addEntities(entities);
    }
}
