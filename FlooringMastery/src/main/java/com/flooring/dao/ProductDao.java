package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Product;
import com.flooring.model.Tax;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts() throws PersistenceException;
}
