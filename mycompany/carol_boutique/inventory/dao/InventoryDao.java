/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.inventory.dao;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.inventory.model.Inventory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public interface InventoryDao {
    boolean addToInventory(List<Item> items, Employee employee);
    List<Stock> lowStock(Employee employee);
    Item getItemByProductId(int productId);
   
}
