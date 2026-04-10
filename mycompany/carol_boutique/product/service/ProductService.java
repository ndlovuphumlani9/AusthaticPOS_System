/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.product.service;

import com.google.zxing.WriterException;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.product.model.Barcode;
import com.mycompany.carol_boutique.product.model.Product;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tau
 */

public interface ProductService {
    int addProduct(Product product);
    Item getItemByBarcode(String barcodeData);
    Barcode generateBarcode(int productCode, String size, String color) throws IOException, WriterException;
    boolean addKeepAside(String barcode2, String email, Employee employee);
    int process(List<Item> items, Employee employee, double total, String email, Long accountNo);
    Employee manager(String username, String password);
    String storeAddress(int storeId);

    int getStoreId(int ibtId3);
}
