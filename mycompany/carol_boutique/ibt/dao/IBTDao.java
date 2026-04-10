/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.dao;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.IBT;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.ibt.model.Store;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public interface IBTDao {
    List<Stock> companyAvailability(String code, int quantity);
    boolean requestIBT(Stock stock, Employee employee, String customer);
    List<IBT> allIBTs(int storeId);
    List<Store> allStores();
    List<IBT> pending(int storeId);
    boolean approve(int ibtId);
    boolean cancel(int id);
    void updateIBT();
    String storeAddress(int storeId);
    boolean receive(int ibtId);
}
