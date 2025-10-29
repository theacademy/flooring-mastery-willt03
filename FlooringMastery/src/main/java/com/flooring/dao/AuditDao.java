package com.flooring.dao;

import com.flooring.exceptions.PersistenceException;

public interface AuditDao {
    void writeAuditEntry(String entry) throws PersistenceException;
}