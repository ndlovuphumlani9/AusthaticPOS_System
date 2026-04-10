/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.store.dao;

import com.mycompany.carol_boutique.employee.dao.EmployeeDao;
import com.mycompany.carol_boutique.store.model.Store;
import java.util.List;

/**
 *
 * @author Poloko
 */
public interface StoreDao {

    void setEmployeeDao(EmployeeDao employeeDao);

    boolean addStore(Store store);

    boolean updateStore(Store store);

    boolean deleteStore(int storeId); // Add this method

    List<Store> getAllStores(); // Add this method

    Store getStoreById(int storeId);
    
    int getTotalStores();
       
}
