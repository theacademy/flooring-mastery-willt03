package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Tax;
import com.flooring.model.Product;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ProductDaoImpl implements ProductDao{
    private static final String PRODUCT_FILE = "FlooringMastery/src/main/resources/Data/Products.txt";
    private static final String DELIMETER = ",";
    private final Map<String, Product> allProducts = new HashMap<>();

    private void loadFile() throws PersistenceException {
        allProducts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String currentLine = reader.readLine();
            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(DELIMETER); //tokening and reading the product.txt file
                Product currentProduct = new Product();
                currentProduct.setProductType(tokens[0]);
                currentProduct.setCostPerSquareFoot(new BigDecimal(tokens[1]));
                currentProduct.setLaborCostPerSquareFoot(new BigDecimal(tokens[2]));
                allProducts.put(currentProduct.getProductType(), currentProduct);
            }
        } catch (IOException e) {
            throw new PersistenceException("ERROR: Couldn't load product data", e);
        }
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        loadFile();
        return new ArrayList<>(allProducts.values());
    }

}
