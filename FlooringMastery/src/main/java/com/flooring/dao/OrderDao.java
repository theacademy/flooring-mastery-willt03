package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> getOrdersByDate(LocalDate date) throws PersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;

    Order addOrder(Order order) throws PersistenceException;

    Order editOrder(LocalDate date, int orderNumber) throws PersistenceException;

    Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException;

    Map<LocalDate, Map<Integer, Order>> getAllOrders() throws PersistenceException;

    int getNextOrderNumber() throws PersistenceException;
}
