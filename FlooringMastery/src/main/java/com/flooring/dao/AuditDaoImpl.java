package com.flooring.dao;

import org.springframework.stereotype.Repository;

@Repository
public abstract class AuditDaoImpl implements AuditDao{

    private static final String AUDIT_FILE = "src/main/resources/Data/Audit.txt";

    public abstract void writeAuditEntry(String entry);

    //didnt implement properly
}
