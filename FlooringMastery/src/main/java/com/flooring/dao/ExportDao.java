package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;

import java.time.LocalDate;
import java.util.Map;

public interface ExportDao {
    void exportData(Map<LocalDate, Map<Integer, Order>> allOrders) throws PersistenceException;
}
