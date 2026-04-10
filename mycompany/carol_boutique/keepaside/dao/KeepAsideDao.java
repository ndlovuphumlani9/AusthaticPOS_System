/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.dao;

import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.keepaside.model.KeepAside;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public interface KeepAsideDao {
    String getProduct(String productId);
    boolean addKeepAside(String barcode, String email, Employee employee);
    void updateKeepAside();
    List<KeepAside> allKeepAside(int storeId);
}
