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
public class IBT {
    int ibtId;
    String from;
    String to;
    String barcode;
    int quantity;
    Status status;

    public IBT(String barcode, int quantity) {
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public IBT(int ibtId, String to, String barcode, int quantity, Status status) {
        this.ibtId = ibtId;
        this.to = to;
        this.barcode = barcode;
        this.quantity = quantity;
        this.status = status;
    }
    
    
}
