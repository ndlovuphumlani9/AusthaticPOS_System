/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Mrqts
 */
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class KeepAside {
    int keepAsideId;
    int storeId;
    int employeeId;
    String barcode;
    Status status;
    String email;
}
