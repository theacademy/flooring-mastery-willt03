package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderDaoImplTest {

    private static final String TEST_FOLDER = "FlooringMastery/src/test/resources/Orders/";
    private OrderDaoImpl dao;

    @BeforeEach
    public void setUp() throws Exception {
        File folder = new File(TEST_FOLDER);

        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                file.delete();
            }
        } else {
            folder.mkdirs();
        }

        dao = new OrderDaoImpl();
        dao.ORDER_FOLDER = TEST_FOLDER;
    }

    @Test
    public void testAddOrder() throws PersistenceException {
        LocalDate date = LocalDate.of(2025, 6, 1);
        Order order = new Order();
        order.setOrderDate(date);
        order.setOrderNumber(1);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("200"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.calculateCosts();

        dao.addOrder(order);
        Order retrieved = dao.getOrder(date, 1);


        assertNotNull(retrieved,"Order recieved");
        assertEquals("Ada Lovelace", retrieved.getCustomerName());
        assertEquals(new BigDecimal("200"), retrieved.getArea());
    }

    @Test
    public void testGetNextOrderNumber() throws PersistenceException {
        int first = dao.getNextOrderNumber();
        int second = dao.getNextOrderNumber();
        assertEquals(first + 1, second);
    }
}
