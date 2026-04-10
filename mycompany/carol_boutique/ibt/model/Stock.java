/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Mrqts
 */
@AllArgsConstructor@NoArgsConstructor@Setter@Getter
public class Stock {
    int storeId;
    String storeAddress;
    String barcode;
    int quantity;
    String colour;
    String size;

    public Stock(String barcode, int quantity, String colour, String size) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.colour = colour;
        this.size = size;
    }

    public Stock(String storeAddress, String barcode, int quantity, String colour, String size) {
        this.storeAddress = storeAddress;
        this.barcode = barcode;
        this.quantity = quantity;
        this.colour = colour;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Stock{" + "storeId=" + storeId + ", storeAddress=" + storeAddress + ", barcode=" + barcode + ", quantity=" + quantity + ", colour=" + colour + ", size=" + size + '}';
    }
    
    
}
