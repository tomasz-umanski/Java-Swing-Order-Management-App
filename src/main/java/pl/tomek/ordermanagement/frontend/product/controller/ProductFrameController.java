package pl.tomek.ordermanagement.frontend.product.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductCreateDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductDto;
import pl.tomek.ordermanagement.backend.facade.product.api.ProductFacade;
import pl.tomek.ordermanagement.backend.facade.product.exception.ProductCreateDtoValidatorException;
import pl.tomek.ordermanagement.frontend.commons.AbstractFrameController;
import pl.tomek.ordermanagement.frontend.commons.Notifications;
import pl.tomek.ordermanagement.frontend.product.model.ProductTableModel;
import pl.tomek.ordermanagement.frontend.product.view.ProductButtonPanel;
import pl.tomek.ordermanagement.frontend.product.view.ProductFrame;
import pl.tomek.ordermanagement.frontend.product.view.modal.ProductDialog;
import pl.tomek.ordermanagement.frontend.product.view.modal.ProductFormPanel;
import pl.tomek.ordermanagement.frontend.product.view.modal.ProductModalButtonPanel;

import javax.swing.*;
import java.util.List;

@Controller
public class ProductFrameController extends AbstractFrameController {
    private final ProductFrame productFrame;
    private final ProductTableModel productTableModel;
    private final ProductDialog productDialog;
    private final ProductFacade productFacade;

    @Autowired
    public ProductFrameController(ProductFrame productFrame,
                                  ProductTableModel productTableModel,
                                  ProductDialog productDialog,
                                  ProductFacade productFacade) {
        this.productFrame = productFrame;
        this.productTableModel = productTableModel;
        this.productDialog = productDialog;
        this.productFacade = productFacade;
    }

    @PostConstruct
    private void prepare() {
        prepareListeners();
    }

    @Override
    public void initAndOpenFrame() {
        loadEntities();
        productFrame.setVisible(true);
    }

    private void loadEntities() {
        List<ProductDto> entities = productFacade.getAllProducts();
        productTableModel.clear();
        productTableModel.addEntities(entities);
    }

    private void prepareListeners() {
        ProductButtonPanel productButtonPanel = productFrame.productButtonPanel();
        ProductModalButtonPanel productAdditionButtonPanel = productDialog.productAdditionButtonPanel();

        registerAction(productButtonPanel.addButton(), e -> showAddModal());
        registerAction(productButtonPanel.deleteButton(), e -> removeEntity());
        registerAction(productButtonPanel.detailsButton(), e -> showDetailsModal());

        registerAction(productAdditionButtonPanel.cancelButton(), e -> hideAddModal());
        registerAction(productAdditionButtonPanel.saveButton(), e -> saveEntity());
    }

    private void saveEntity() {
        ProductFormPanel productFormPanel = productDialog.productFormPanel();
        try {
            ProductCreateDto productCreateDto = productFormPanel.toCreateDto();
            ProductDto createdProductDto = productFacade.saveProduct(productCreateDto);
            productTableModel.addEntity(createdProductDto);
            hideAddModal();
        } catch (ProductCreateDtoValidatorException e) {
            Notifications.showFormValidationAlert(e.getMessage());
        }
    }

    private void hideAddModal() {
        productDialog.productFormPanel().clearForm();
        productDialog.dispose();
    }

    private void showAddModal() {
        productDialog.prepareAddDialog();
        productDialog.setVisible(true);
    }

    private void removeEntity() {
        try {
            JTable productTable = productFrame.productTablePanel().table();
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "No row selected",
                        "Alert",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                ProductDto entity = productTableModel.getEntityByRow(selectedRow);
                productFacade.deleteProduct(entity.id());
                productTableModel.removeRow(selectedRow);
            }
        } catch (Exception e) {
            Notifications.showDeleteRowErrorMessage();
        }
    }

    private void showDetailsModal() {
        JTable productTable = productFrame.productTablePanel().table();
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No row selected",
                    "Alert",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            ProductDto productDto = productTableModel.getEntityByRow(selectedRow);
            productDialog.prepareDetailsDialog(productDto);
            productDialog.setVisible(true);
        }
    }
}
