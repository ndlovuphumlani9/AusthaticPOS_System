/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.returns.service;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.returns.model.Reason;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public interface ReturnService {
    List<Item> returns(int saleId);
    String[] processReturn(int itemId, Reason reason, Employee employee);
    int processExchange(int saleItemId, String barcode);
    String storeAddress(int storeId);
    List<Item> getReceipt(int saleId);
}
