package com.flooring.service;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Order;
import com.flooring.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.Assert.assertEquals;

public class ServiceLayerImplTest {

    private ServiceLayer service;

    @BeforeEach
    public void setUp() {
        service = new ServiceLayerImpl(
                new OrderDaoStubImpl(),
                new ProductDaoStubImpl(),
                new TaxDaoStubImpl(),
                new ExportDaoStubImpl()
        );
    }

    @Test
    public void testAddAndGetOrder() throws PersistenceException {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Test User");
        order.setState("TX");
        order.setProductType("Tile");
        order.setArea(new BigDecimal("200"));
        order.setOrderDate(LocalDate.now().plusDays(1));

        service.addOrder(order);

        Order fromService = service.getOrder(order.getOrderDate(), 1);
        assertEquals(order, fromService);
    }

}
