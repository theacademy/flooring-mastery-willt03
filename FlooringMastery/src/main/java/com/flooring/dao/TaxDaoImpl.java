package com.flooring.dao;

import org.springframework.stereotype.Repository;
import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Product;
import com.flooring.model.Tax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Repository
public class TaxDaoImpl implements TaxDao {

    private static final String TAX_FILE = "FlooringMastery/src/main/resources/Data/Taxes.txt";
    private static final String DELIMETER = ",";
    private Map<String, Tax> allTaxes = new HashMap<>();

    @Override
    public List<Tax> getAllTaxes() throws PersistenceException{
        loadFile();
        return new ArrayList<>(allTaxes.values());
    }

    private void loadFile() throws PersistenceException {
        allTaxes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(TAX_FILE))) {
            String currentLine;
            reader.readLine();
            while ((currentLine = reader.readLine()) != null) {
                String[] tokens = currentLine.split(DELIMETER);//tokening and reading the taxes.txt in the resources folder

                Tax currentTax = new Tax();
                currentTax.setStateAbr(tokens[0]);
                currentTax.setState(tokens[1]);
                currentTax.setTaxRate(new BigDecimal(tokens[2]));
                allTaxes.put(currentTax.getStateAbr(), currentTax);
            }


        } catch (IOException e) {
            throw new PersistenceException("ERROR: Couldn't load tax data", e);
        }
    }


}
