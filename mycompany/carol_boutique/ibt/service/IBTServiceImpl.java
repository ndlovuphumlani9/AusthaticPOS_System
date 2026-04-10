/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.service;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.dao.IBTDao;
import com.mycompany.carol_boutique.ibt.model.IBT;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.ibt.model.Store;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public class IBTServiceImpl implements IBTService {
    IBTDao ibtDao;

    public IBTServiceImpl(IBTDao ibtDao) {
        this.ibtDao = ibtDao;
    }

    @Override
    public List<Stock> companyAvailability(String code, int quantity) {
        return ibtDao.companyAvailability(code, quantity);
    }

    @Override
    public boolean requestIBT(Stock stock, Employee employee, String customer) {
        return ibtDao.requestIBT(stock, employee, customer);
    }

    @Override
    public List<IBT> allIBTs(int storeId) {
         return ibtDao.allIBTs(storeId);
    }

    @Override
    public List<Store> allStores() {
         return ibtDao.allStores();
    }

    @Override
    public List<IBT> pending(int storeId) {
        return ibtDao.pending(storeId);
    }

    @Override
    public boolean approve(int ibtId) {
        return ibtDao.approve(ibtId);
    }

    @Override
    public boolean cancel(int id) {
        return ibtDao.cancel(id);
    }

    @Override
    public void updateIBT() {
        ibtDao.updateIBT();
    }

    @Override
    public String storeAddress(int storeId) {
        return ibtDao.storeAddress(storeId);
    }

    @Override
    public boolean receive(int ibtId) {
        return ibtDao.receive(ibtId);
    }
}
