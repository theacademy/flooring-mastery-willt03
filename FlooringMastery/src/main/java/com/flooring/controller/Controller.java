package com.flooring.controller;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;
import com.flooring.model.Product;
import com.flooring.model.Tax;
import com.flooring.service.ServiceLayer;
import com.flooring.view.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Component
public class Controller {

    private ServiceLayer service;
    private View view;

    @Autowired
    public Controller(ServiceLayer service, View view) { //Constructor
        this.service = service;
        this.view = view;
    }

    public void run() { //main sequence
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportData();
                    break;
                case 6:
                    exitMessage();
                    keepGoing = false;
                    break; //closing the loop
                default:
                    unknownCommand();
            }
        }
    }

    public int getMenuSelection() {
        return view.displayMainMenuAndGetSelection();
    }

    public void displayOrders() {
        LocalDate date = view.getDateInput();
        try {
            List<Order> orders = service.getOrdersForDate(date);
            view.displayOrders(orders);
        } catch (PersistenceException e) {
            view.displayErrorMessage("ERROR: Could not load orders: " + e.getMessage());
        }
    }

    public void addOrder() {
        view.displayAddOrderBanner();

        try {
            List<Tax> taxes = service.getTaxes();
            List<Product> products = service.getProducts();

            Order newOrder = view.getAddOrderInput(taxes, products);
            int orderNumber = service.getNextOrderNumber();
            newOrder.setOrderNumber(orderNumber);
            newOrder.setOrderDate(view.getDateInput());

            newOrder.calculateCosts();
            view.displayOrderInfo(newOrder);
            boolean confirm = view.getConfirmation();

            if (confirm) {
                service.addOrder(newOrder);
                view.displayAddOrderSuccess();
            } else {
                view.displayErrorMessage("Order not saved.");
            }
        } catch (PersistenceException e) {
            view.displayErrorMessage("ERROR: Could not save order: " + e.getMessage());
        }
    }

    public void editOrder() {
        view.displayEditOrderBanner();

        LocalDate date = view.getDateInput();
        int orderNumber = view.getOrderNumberInput();
        try {
            // Try to get the existing order
            Order existingOrder = service.getOrder(date, orderNumber);

            if (existingOrder == null) {
                view.displayErrorMessage("No such order found for that date and number.");
                return;
            }

            // Get updated input from user
            List<Tax> taxes = service.getTaxes();
            List<Product> products = service.getProducts();

            Order editedOrder = view.getEditOrderInput(existingOrder, taxes, products);

            view.displayOrderInfo(editedOrder);
            boolean confirm = view.getConfirmation();

            if (confirm) {
                service.editOrder(date, orderNumber);
                view.displayEditOrderSuccess();
            } else {
                view.displayErrorMessage("Edit cancelled.");
            }

        } catch (PersistenceException e) {
            view.displayErrorMessage("ERROR: Could not edit order: " + e.getMessage());
        }
    }

    public void removeOrder() {
        view.displayRemoveOrderBanner();

        LocalDate date = view.getDateInput();
        int orderNumber = view.getOrderNumberInput();
        try {
            Order order = service.getOrder(date, orderNumber);

            if (order == null) {
                view.displayErrorMessage("No such order found.");
                return;
            }

            view.displayOrderInfo(order);
            boolean confirm = view.getConfirmation();

            if (confirm) {
                service.removeOrder(date, orderNumber);
                view.displayRemoveOrderSuccess();
            } else {
                view.displayErrorMessage("Order not removed.");
            }
        } catch (PersistenceException e) {
            view.displayErrorMessage("ERROR: Could not remove order: " + e.getMessage());
        }
    }

    private void exportData() {}; //add later

    public void exitMessage() {
        view.displayExitMessage();
    }

    public void unknownCommand() {
        view.displayUnknownCommandMessage();
    }


}
