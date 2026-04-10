/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.inventory.service;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.inventory.dao.InventoryDao;
import com.mycompany.carol_boutique.inventory.model.Inventory;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public class InventoryServiceImpl implements InventoryService {
    InventoryDao inventoryDao;

    public InventoryServiceImpl(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Override
    public boolean addToInventory(List<Item> items, Employee employee) {
        return inventoryDao.addToInventory(items, employee);
    }

    @Override
    public List<Stock> lowStock(Employee employee) {
        return inventoryDao.lowStock(employee);
    }


    @Override
    public Item getItemByProductId(int productId) {
        return inventoryDao.getItemByProductId(productId);
    }

    
    
}
