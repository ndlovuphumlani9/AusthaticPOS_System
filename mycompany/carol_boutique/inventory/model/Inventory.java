/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.inventory.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Acer
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private int inventoryId;
    private int storeId;
    private int productId;
    private int quantity;
    private Timestamp lastUpdated;

    public Inventory(int storeId, int productId, int quantity) {
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        
    }
}
