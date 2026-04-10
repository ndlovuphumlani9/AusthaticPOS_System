/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.topArchievingStoresSales.model;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Poloko
 */

@Setter
@Getter
public class SalesReport {
    private int saleId;
    private int productId;
    private String productName;
    private int quantitySold;

   

    public void setSaleTimestamp(String saleTimestamp) {
        this.saleTimestamp = saleTimestamp;
    }
    private double totalAmount;
    private int storeId;
    private String saleTimestamp;

    public void setCustomerId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setEmployeeId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSalesDate(Timestamp timestamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
