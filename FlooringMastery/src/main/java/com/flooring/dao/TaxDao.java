package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;
import com.flooring.model.Tax;
import java.util.List;

public interface TaxDao {
    List<Tax> getAllTaxes() throws PersistenceException;
}