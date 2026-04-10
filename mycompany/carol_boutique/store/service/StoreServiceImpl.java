/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.store.service;

/**
 *
 * @author Poloko
 */



import com.mycompany.carol_boutique.employee.dao.EmployeeDao;
import com.mycompany.carol_boutique.store.dao.StoreDao;
import com.mycompany.carol_boutique.store.dao.StoreDaoImpl;
import com.mycompany.carol_boutique.store.model.Store;
import java.util.List;


public class StoreServiceImpl implements StoreService {
    private StoreDao storeDao;

    public StoreServiceImpl(StoreDao storeDao) {
        this.storeDao = new StoreDaoImpl(); 
    }
    public StoreServiceImpl(StoreDao storeDao, EmployeeDao employeeDao) {
    this.storeDao = storeDao;
    this.storeDao.setEmployeeDao(employeeDao); // Add this setter to your StoreDao
}


    @Override
    public boolean addStore(Store store) {
        return storeDao.addStore(store);
    }
    
      @Override
    public boolean updateStore(Store store) {
        return storeDao.updateStore(store);
    }
    
     @Override
    public boolean deleteStore(int storeId) {
        return storeDao.deleteStore(storeId); // Implement this in DAO
    }

    @Override
    public List<Store> getAllStores() {
        return storeDao.getAllStores(); // Implement this in DAO
    }
    @Override
    public Store getStoreById(int storeId) {
        return storeDao.getStoreById(storeId);
    }
    
    public int getTotalStores() {
        return storeDao.getTotalStores(); // Call to DAO method to get the count
    }
}

