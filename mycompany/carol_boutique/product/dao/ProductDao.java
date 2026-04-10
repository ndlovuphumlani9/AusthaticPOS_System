/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.product.dao;

import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.product.model.Product;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public interface ProductDao {
    int addProduct(Product product);
    Item getItemByBarcode(String barcodeData);
    boolean addKeepAside(String barcode2, String email, Employee employee);
    int process(List<Item> items, Employee employee, double total, String email, Long cardNo);
    Employee manager(String username, String password);
    String storeAddress(int storeId);

    int getStoreId(int ibtId3);
}