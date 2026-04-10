/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter 
@Getter
@NoArgsConstructor@AllArgsConstructor
public class StoreReport {
    private int storeId;
    private String storeName;
    private String location;
    private BigDecimal targetAmount;
    private BigDecimal totalSales;
}
