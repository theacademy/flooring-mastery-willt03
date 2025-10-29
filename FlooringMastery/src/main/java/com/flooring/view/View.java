package com.flooring.view;

import com.flooring.model.Order;
import com.flooring.model.Tax;
import com.flooring.model.Product;
import com.flooring.ui.UserIO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class View {

    private final UserIO io;

    @Autowired
    public View(UserIO io) {
        this.io = io;
    }

    public int displayMainMenuAndGetSelection() { //main menu
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the above choices. (1-6)", 1, 6);
    }

    public LocalDate getDateInput() { //get the date
        String dateInput = io.readString("Enter the order date (dd-MM-yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateInput, formatter);
    }

    public void displayOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            io.print("No orders on that date.");
            return;
        }
        for (Order order : orders) {
            io.print(order.toString());
        }
    }

    public void displayOrderInfo(Order order) {
        io.print("-------------------------------------");
        io.print("Order #: " + order.getOrderNumber());
        io.print("Customer: " + order.getCustomerName());
        io.print("State: " + order.getState() + " (" + order.getTaxRate() + "%)");
        io.print("Product: " + order.getProductType());
        io.print("Area: " + order.getArea() + " sq ft");
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
    }

    public void displayAddOrderBanner() {
        io.print("=== Add Order ===");
    }

    public Order getAddOrderInput(List<Tax> taxes, List<Product> products) { //the taxes and products list are already loaded
        String customerName = validateNameInput(io.readString("Enter customer name: "));

        io.print("Available States:");
        for (Tax t : taxes) { //checks against the Taxes.txt file
            io.print(t.getStateAbr() + " - " + t.getState() + " (" + t.getTaxRate() + "%)"); //Displays available States
        }
        String stateInput = io.readString("Enter state abbreviation: ");
        Tax tax = validateStateInput(stateInput, taxes);

        io.print("Available Products:");
        for (Product p : products) {
            io.print(p.getProductType() + " (Material: $" + p.getCostPerSquareFoot()
                    + ", Labor: $" + p.getLaborCostPerSquareFoot() + ")"); //Displays available products
        }
        String productInput = io.readString("Enter product type: ");
        Product product = validateProductInput(productInput, products);

        String areaStr = io.readString("Enter area (minimum 100 sq ft): ");
        BigDecimal area = validateAreaInput(areaStr);

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setState(tax.getStateAbr());
        order.setTaxRate(tax.getTaxRate());
        order.setProductType(product.getProductType());
        order.setArea(area);
        order.calculateCosts();
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());

        return order;
    }

    public void displayAddOrderSuccess() {
        io.print("Order successfully added!");
    }

    public void displayEditOrderBanner() {
        io.print("=== Edit Order ===");
    }

    public int getOrderNumberInput() {
        return io.readInt("Enter order number: ");
    }


    public Order getEditOrderInput(Order existingOrder, List<Tax> taxes, List<Product> products) {
        String name = io.readString("Enter customer name (" + existingOrder.getCustomerName() + "): ");
        if (!name.trim().isEmpty()) existingOrder.setCustomerName(validateNameInput(name));

        String stateInput = io.readString("Enter state (" + existingOrder.getState() + "): ");
        if (!stateInput.trim().isEmpty()) {                    // all check for no input
            Tax tax = validateStateInput(stateInput, taxes);
            existingOrder.setState(tax.getStateAbr());
            existingOrder.setTaxRate(tax.getTaxRate());
        }

        String productInput = io.readString("Enter product type (" + existingOrder.getProductType() + "): ");
        if (!productInput.trim().isEmpty()) {
            Product product = validateProductInput(productInput, products);
            existingOrder.setProductType(product.getProductType());
            existingOrder.setCostPerSquareFoot(product.getCostPerSquareFoot());
            existingOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        }

        String areaStr = io.readString("Enter area (" + existingOrder.getArea() + "): ");
        if (!areaStr.trim().isEmpty()) {
            BigDecimal area = validateAreaInput(areaStr);
            existingOrder.setArea(area);
        }

        existingOrder.calculateCosts();

        return existingOrder;
    }

    public void displayEditOrderSuccess() {
        io.print("Order successfully edited!");
    }

    public void displayRemoveOrderBanner() {
        io.print("=== Remove Order ===");
    }

    public boolean getConfirmation() {
        String input = io.readString("Are you sure? (Y/N): ");
        return input.equalsIgnoreCase("Y"); // allows for lower case y as well
    }

    public void displayRemoveOrderSuccess() {
        io.print("Order successfully removed!");
    }

    public void displayExportDataSuccess() {
        io.print("All data successfully exported!");
    }

    public void displayExitMessage() {
        io.print("Goodbye!");
    }

    public void displayErrorMessage(String message) {
        io.print("ERROR: " + message);
    }

    public void displayUnknownCommandMessage() {
        io.print("Unknown command.");
    }

    private String validateNameInput(String input) {
        while (true) {
            input = input.trim();
            if (input.isEmpty()) {
                input = io.readString("Name cannot be blank. Enter customer name: ");
                continue;
            }

            boolean valid = true;
            for (char c : input.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '.' && c != ',' && c != ' ') { //Allows for A-Z, a-z numbers and a few ascii but nothing more.
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return input;
            } else {
                io.print("Invalid name. Use only letters, numbers, spaces, commas, or periods.");
                input = io.readString("Enter customer name: ");
            }
        }
    }

    private Tax validateStateInput(String stateAbr, List<Tax> taxes) {
        for (Tax t : taxes) {
            if (t.getStateAbr().equalsIgnoreCase(stateAbr)) {
                return t;
            }
        }
        io.print("State not found. Please enter a valid state abbreviation.");
        String newInput = io.readString("Enter state abbreviation: ");
        return validateStateInput(newInput, taxes);
    }

    private Product validateProductInput(String productType, List<Product> products) {
        for (Product p : products) {
            if (p.getProductType().equalsIgnoreCase(productType)) {
                return p;
            }
        }
        io.print("Product not found. Please enter a valid product type.");
        String newInput = io.readString("Enter product type: ");
        return validateProductInput(newInput, products);
    }

    private BigDecimal validateAreaInput(String inputArea) { // checks for at least 100 sq ft
        BigDecimal hundred = BigDecimal.valueOf(100);
        while (true) {
            BigDecimal area = new BigDecimal(inputArea);
            if (area.compareTo(hundred) >= 0) {
                return area;
            } else {
                io.print("Minimum area is 100 sq ft.");
            }
            inputArea = io.readString("Enter area (minimum 100 sq ft): ");
        }
    }




}
