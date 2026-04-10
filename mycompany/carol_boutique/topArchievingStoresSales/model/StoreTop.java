/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.topArchievingStoresSales.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Poloko
 */
@Setter
@Getter
public class StoreTop {
    private int storeId;//store  table
    private String storeName;//store table
    private double totalSales;//sales table
     private double averageRating;//from rating table
      
     // From sales table
    private int saleId;
    
    // From sales table
    private int employeeId;
    
    // From sales table
    private int customerId;
    
    // From sales table
    private BigDecimal totalAmount;
    
    // From sales table
    private Timestamp salesDate;

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Timestamp salesDate) {
        this.salesDate = salesDate;
    }

    

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    
}
