package com.flooring.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private String customerName;
    private String state;
    private LocalDate orderDate;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal area;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    // constructor
    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate,
                 String productType, BigDecimal area, BigDecimal costPerSquareFoot,
                 BigDecimal laborCostPerSquareFoot) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        calculateCosts();
    }

    public Order() {};

    public void calculateCosts() { // calulates the cost of the leftover parameters
        if (area == null || costPerSquareFoot == null || laborCostPerSquareFoot == null || taxRate == null) {
            return; // Don’t calculate if required fields are missing
        }

        this.materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);


        this.laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);


        BigDecimal costAndLabor = materialCost.add(laborCost);
        this.tax = costAndLabor.multiply(taxRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);


        this.total = costAndLabor.add(tax).setScale(2, RoundingMode.HALF_UP);

    }

    // Getters and setters
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber;
    }

    @Override
    public String toString() {
        return orderNumber + "," +
                customerName + "," +
                state + "," +
                taxRate + "," +
                productType + "," +
                area + "," +
                costPerSquareFoot + "," +
                laborCostPerSquareFoot + "," +
                materialCost + "," +
                laborCost + "," +
                tax + "," +
                total;
    }

}
