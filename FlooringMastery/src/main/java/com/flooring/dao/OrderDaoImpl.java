package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String DELIMETER = ",";
    public String ORDER_FOLDER = "FlooringMastery/src/main/resources/Orders/";
    private Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();
    private int largestOrderNumber;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");


    private void loadFromFile() throws PersistenceException { //loading the order from the .txt
        orders.clear();
        File folder = new File(ORDER_FOLDER);
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            String fileName = file.getName();
            String orderDate = fileName.substring(7, 15); //Gets the date chars from the file name
            LocalDate fileDate = LocalDate.parse(orderDate, formatter);

            Map<Integer, Order> ordersOnDate = new HashMap<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine(); // header line
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    String[] tokens = currentLine.split(DELIMETER);//gets rid of the commas and tokens the rest of the data
                    Order order = new Order();
                    order.setOrderNumber(Integer.parseInt(tokens[0]));
                    order.setCustomerName(tokens[1]);
                    order.setState(tokens[2]);
                    order.setTaxRate(new BigDecimal(tokens[3]));
                    order.setProductType(tokens[4]);
                    order.setArea(new BigDecimal(tokens[5]));
                    order.setCostPerSquareFoot(new BigDecimal(tokens[6]));
                    order.setLaborCostPerSquareFoot(new BigDecimal(tokens[7]));
                    order.setMaterialCost(new BigDecimal(tokens[8]));
                    order.setLaborCost(new BigDecimal(tokens[9]));
                    order.setTax(new BigDecimal(tokens[10]));
                    order.setTotal(new BigDecimal(tokens[11]));
                    order.setOrderDate(fileDate);

                    ordersOnDate.put(order.getOrderNumber(), order);
                    if (order.getOrderNumber() > largestOrderNumber) {
                        largestOrderNumber = order.getOrderNumber();
                    }
                }
                orders.put(fileDate, ordersOnDate);
            } catch (IOException e) {
                throw new PersistenceException("ERROR: Unable to read file " + fileName, e);
            }
        }

    }

    private void writeToFile() throws PersistenceException { //reverse of read, formating the data into the string
        for (Map.Entry<LocalDate, Map<Integer, Order>> entry : orders.entrySet()) {
            LocalDate orderDate = entry.getKey();
            String fileName = ORDER_FOLDER + "Orders_" + orderDate.format(formatter) + ".txt";

            try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                //header
                writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
                for (Order o : entry.getValue().values()) {
                    writer.println(o.getOrderNumber() + DELIMETER
                            + o.getCustomerName() + DELIMETER
                            + o.getState() + DELIMETER
                            + o.getTaxRate() + DELIMETER
                            + o.getProductType() + DELIMETER
                            + o.getArea() + DELIMETER
                            + o.getCostPerSquareFoot() + DELIMETER
                            + o.getLaborCostPerSquareFoot() + DELIMETER
                            + o.getMaterialCost() + DELIMETER
                            + o.getLaborCost() + DELIMETER
                            + o.getTax() + DELIMETER
                            + o.getTotal());
                }
            } catch (IOException e) {
                throw new PersistenceException("Error Unable to write file " + fileName, e);
            }
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws PersistenceException { //
        loadFromFile();
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) return new ArrayList<>();
        return new ArrayList<>(ordersForDate.values());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        loadFromFile();
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) return null;
        return ordersForDate.get(orderNumber);
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        loadFromFile();
        LocalDate date = order.getOrderDate();

        Map<Integer, Order> dailyOrders = orders.get(date);
        if (dailyOrders == null) {
            dailyOrders = new HashMap<>();
            orders.put(date, dailyOrders);
        }
        dailyOrders.put(order.getOrderNumber(), order);
        writeToFile();
        return order;
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber) throws PersistenceException {
        loadFromFile();
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) return null;
        return ordersForDate.get(orderNumber);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        loadFromFile();
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) return null;
        Order removed = ordersForDate.remove(orderNumber);
        writeToFile();
        return removed;
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() throws PersistenceException {
        loadFromFile();
        return orders;
    }

    @Override
    public int getNextOrderNumber() {
        largestOrderNumber++;
        return largestOrderNumber;
    }


}
