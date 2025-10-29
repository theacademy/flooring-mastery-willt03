package com.flooring.stubs;

import com.flooring.dao.OrderDao;
import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderDaoStubImpl implements OrderDao {

    public Order onlyOrder;

    public OrderDaoStubImpl() {
        onlyOrder = new Order();
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("Ada Lovelace");
        onlyOrder.setState("CA");
        onlyOrder.setTaxRate(new BigDecimal("25.00"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("249.00"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        onlyOrder.calculateCosts();
        onlyOrder.setOrderDate(LocalDate.of(2025, 6, 1));
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        if (order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return order;
        }
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber) throws PersistenceException {
        return getOrder(date, orderNumber);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        if (orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws PersistenceException {
        return List.of(onlyOrder);
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() throws PersistenceException {
        Map<Integer, Order> map = new HashMap<>();
        map.put(onlyOrder.getOrderNumber(), onlyOrder);
        Map<LocalDate, Map<Integer, Order>> outer = new HashMap<>();
        outer.put(onlyOrder.getOrderDate(), map);
        return outer;
    }

    @Override
    public int getNextOrderNumber() {
        return onlyOrder.getOrderNumber() + 1;
    }
}