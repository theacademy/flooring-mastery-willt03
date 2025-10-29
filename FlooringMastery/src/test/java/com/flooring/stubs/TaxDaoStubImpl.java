package com.flooring.stubs;

import com.flooring.dao.TaxDao;
import com.flooring.model.Tax;

import java.util.List;

public class TaxDaoStubImpl implements TaxDao {
    @Override
    public List<Tax> getAllTaxes() { return List.of(); }
}
