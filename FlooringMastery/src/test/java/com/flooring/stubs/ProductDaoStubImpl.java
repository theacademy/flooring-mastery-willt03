package com.flooring.stubs;

import com.flooring.dao.ProductDao;
import com.flooring.model.Product;

import java.util.List;

public class ProductDaoStubImpl implements ProductDao {
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}

