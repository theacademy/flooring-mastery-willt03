package com.flooring.service;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;
import com.flooring.model.Tax;
import com.flooring.model.Product;

import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {

    int getNextOrderNumber() throws PersistenceException;

    Order addOrder(Order order) throws PersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;

    Order editOrder(LocalDate date, int orderNumber) throws PersistenceException;

    List<Order> getOrdersForDate(LocalDate date) throws PersistenceException;

    Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException;

    void exportData();

    List<Tax> getTaxes() throws PersistenceException;

    List<Product> getProducts() throws PersistenceException;

}
