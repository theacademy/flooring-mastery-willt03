package com.flooring.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {
    private String state;
    private String stateAbr;
    private BigDecimal taxRate;

    // constructor

    public Tax(String state, String stateAbr, BigDecimal taxRate) {
        this.state = state;
        this.stateAbr = stateAbr;
        this.taxRate = taxRate;
    }

    public Tax() { };

    // Getters and Setters
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAbr() {
        return stateAbr;
    }

    public void setStateAbr(String stateAbr) {
        this.stateAbr = stateAbr;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateAbr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return stateAbr.equals(tax.stateAbr);  // Equality check by state abbreviation
    }

    @Override
    public String toString() {
        return stateAbr + "," +
                state + "," +
                taxRate.setScale(2, BigDecimal.ROUND_HALF_UP);  // Ensure taxRate has 2 decimal places
    }


}
