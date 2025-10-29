package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;

import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class ExportDaoImpl implements ExportDao{

    private static final String EXPORT_FILE = "src/main/resources/Backup/DataExport.txt";
    private static final String DELIMITER = ",";
    private List<Order> allOrders = new ArrayList<>();

    private void writeToFile() throws PersistenceException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EXPORT_FILE))) {
            writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,"
                    + "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");

            for (Order o : allOrders) { // formating the string for the order
                writer.println(o.getOrderNumber() + DELIMITER
                        + o.getCustomerName() + DELIMITER
                        + o.getState() + DELIMITER
                        + o.getTaxRate() + DELIMITER
                        + o.getProductType() + DELIMITER
                        + o.getArea() + DELIMITER
                        + o.getCostPerSquareFoot() + DELIMITER
                        + o.getLaborCostPerSquareFoot() + DELIMITER
                        + o.getMaterialCost() + DELIMITER
                        + o.getLaborCost() + DELIMITER
                        + o.getTax() + DELIMITER
                        + o.getTotal() + DELIMITER
                        + o.getOrderDate().format(formatter));
            }
        } catch (IOException e) {
            throw new PersistenceException("ERROR: Unable to export data.", e);
        }
    }

    @Override
    public void exportData(Map<LocalDate, Map<Integer, Order>> allOrders) throws PersistenceException {
        //later update
    }

}
