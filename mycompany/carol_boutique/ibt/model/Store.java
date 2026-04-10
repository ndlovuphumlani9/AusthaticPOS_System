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
public class Store {
    int storeId;
    String managerName;
    String managerSurname;
    String storeName;
    String location;
    String number;
    String email;

    public Store(int storeId, String storeName, String location, String number, String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.number = number;
        this.email = email;
    }
      
}
