/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.topArchievingStoresSales.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Poloko
 */

@Setter
@Getter
public class ProductSales {
    private int productId;
    private String productName;
    private int totalQuantitySold;
    private int storeId;
    private String storeName;
}
