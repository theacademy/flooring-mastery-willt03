package com.flooring.stubs;

import com.flooring.dao.ExportDao;
import com.flooring.model.Order;

import java.time.LocalDate;
import java.util.Map;

public class ExportDaoStubImpl implements ExportDao {
    @Override
    public void exportData(Map<LocalDate, Map<Integer, Order>> allOrders) { }
}
