/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.returns.service;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.returns.dao.ReturnDao;
import com.mycompany.carol_boutique.returns.model.Reason;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public class ReturnServiceImpl implements ReturnService {
    ReturnDao returnDao;

    public ReturnServiceImpl(ReturnDao returnDao) {
        this.returnDao = returnDao;
    }

    @Override
    public List<Item> returns(int saleId) {
        return returnDao.returns(saleId);
    }

    @Override
    public String[] processReturn(int itemId, Reason reason, Employee employee) {
        return returnDao.processReturn(itemId, reason, employee);
    }

    @Override
    public int processExchange(int saleItemId, String barcode) {
        return returnDao.processExchange(saleItemId, barcode);
    }

    @Override
    public String storeAddress(int storeId) {
        return returnDao.storeAddress(storeId);
    }

    @Override
    public List<Item> getReceipt(int saleId) {
        return returnDao.getReceipt(saleId);
    }
    
}
