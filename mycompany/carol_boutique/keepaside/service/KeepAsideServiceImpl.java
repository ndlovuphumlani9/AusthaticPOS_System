/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.service;

import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.keepaside.dao.KeepAsideDao;
import com.mycompany.carol_boutique.keepaside.model.KeepAside;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public class KeepAsideServiceImpl implements KeepAsideService {
    KeepAsideDao keepAsideDao;

    public KeepAsideServiceImpl(KeepAsideDao keepAsideDao) {
        this.keepAsideDao = keepAsideDao;
    }
    
    @Override
    public boolean addKeepAside(String barcode, String email, Employee employee) {
        return keepAsideDao.addKeepAside(barcode, email, employee);
    }

    @Override
    public void updateKeepAside() {
        keepAsideDao.updateKeepAside();
    }


    @Override
    public String getProduct(String productId) {
        return keepAsideDao.getProduct(productId);
    }

    @Override
    public List<KeepAside> allKeepAside(int storeId) {
         return keepAsideDao.allKeepAside(storeId);
    }
    
}
