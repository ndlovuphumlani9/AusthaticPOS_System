/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Poloko
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    private int storeId;
    private String storeName;
    private String location;
    private String storePhone;
    private String storeEmail;

    public Store(String location, String storePhone, String storeEmail) {
        this.location = location;
        this.storePhone = storePhone;
        this.storeEmail = storeEmail;
    }

}
