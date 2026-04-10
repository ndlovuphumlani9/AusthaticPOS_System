/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Mrqts
 */

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class Item {
    String storeAddress;
    int inventoryId;
    int productId;
    double price;
    String size;
    String colour;
    String description;
    String name;
    String barcode;
    int saleItemId;

    public Item(int productId, double price, String size, String colour, String description, String name, String barcode) {
        this.productId = productId;
        this.price = price;
        this.size = size;
        this.colour = colour;
        this.description = description;
        this.name = name;
        this.barcode = barcode;
    } 

    public Item(String storeAddress, int inventoryId, int productId, String size, String colour) {
        this.storeAddress = storeAddress;
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.size = size;
        this.colour = colour;
    }   
    
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Item(double price, String description, String name, String barcode) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.barcode = barcode;
    }

    public Item(String barcode) {
        this.barcode = barcode;
    }

    public Item(double price, String size, String colour, String description, String name, String barcode, int saleItemId) {
        this.price = price;
        this.size = size;
        this.colour = colour;
        this.description = description;
        this.name = name;
        this.barcode = barcode;
        this.saleItemId = saleItemId;
    }
    

//    @Override
//    public String toString() {
//        return "Item{" + "price=" + price + ", description=" + description + ", name=" + name + ", barcode=" + barcode + '}';
//    }

    @Override
    public String toString() {
        return "Item{" + "productId=" + productId + ", price=" + price + ", size=" + size + ", colour=" + colour + ", description=" + description + ", name=" + name + ", barcode=" + barcode + ", saleItemId=" + saleItemId + '}';
    }

    public Item(int productId, double price, String size, String colour, String description, String name, String barcode, int saleItemId) {
        this.productId = productId;
        this.price = price;
        this.size = size;
        this.colour = colour;
        this.description = description;
        this.name = name;
        this.barcode = barcode;
        this.saleItemId = saleItemId;
    }

    public Item(int productId, double price, String size, String colour, String description, String name) {
        this.productId = productId;
        this.price = price;
        this.size = size;
        this.colour = colour;
        this.description = description;
        this.name = name;
    }

    public Item(int productId, double price, String description, String name) {
        this.productId = productId;
        this.price = price;
        this.description = description;
        this.name = name;
    }
    
}
