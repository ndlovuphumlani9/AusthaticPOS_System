/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Acer
 */
@Setter 
@Getter
@NoArgsConstructor@AllArgsConstructor
public class SalesReport {
    private int saleId;
    private int employeeId;
    private int customerId;
    private BigDecimal totalAmount;
    private int storeId;
    private Timestamp salesDate;
    
    public double getTotalAmountAsDouble() {
        return totalAmount.doubleValue(); // 
    }
}
