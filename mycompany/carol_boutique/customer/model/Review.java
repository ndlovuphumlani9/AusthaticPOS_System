/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.customer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Mrqts
 */
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class Review {
    String name;
    String number;
    String email;
    int rating;
    String comment;
    int storeId;
}
