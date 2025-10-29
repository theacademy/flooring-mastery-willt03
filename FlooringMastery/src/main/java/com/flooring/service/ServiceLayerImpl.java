package com.flooring.service;

import com.flooring.dao.*;
import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;
import com.flooring.model.Tax;
import com.flooring.model.Product;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLayerImpl implements ServiceLayer {

    //private AuditDao auditDao;
    private ExportDao exportDao;
    private OrderDao orderDao;
    private TaxDao taxDao;
    private ProductDao productDao;

    //the auditdao was left out as it wasnt used
    @Autowired
    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao, ExportDao exportDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.exportDao = exportDao;
        //this.auditDao = auditDao;
    }

    @Override
    public int getNextOrderNumber() throws PersistenceException{
        return orderDao.getNextOrderNumber();
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        if (order.getArea() != null && order.getTaxRate() != null
                && order.getCostPerSquareFoot() != null && order.getLaborCostPerSquareFoot() != null) {
            order.calculateCosts();
        }
        orderDao.addOrder(order);
         return order;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException{
        return orderDao.getOrder(date, orderNumber);
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber) throws PersistenceException{
        Order currentOrder = orderDao.getOrder(date, orderNumber);
        if (currentOrder != null) {
            currentOrder.calculateCosts();
        }
        return currentOrder;
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException{
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException{
        Order removed = orderDao.removeOrder(date, orderNumber);
        return removed;
    }

    @Override
    public void exportData() {}; //not implemented yet

    @Override
    public List<Tax> getTaxes() throws PersistenceException{
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getProducts() throws PersistenceException{
        return productDao.getAllProducts();
    }

    private void writeToAudit(String string) {}; //not implemented but in UWL







}
