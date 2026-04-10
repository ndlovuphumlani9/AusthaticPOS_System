/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.sale.service;

import com.mycompany.carol_boutique.ibt.model.Item;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public interface SaleService {
    boolean processSale(List<Item> items);
}
