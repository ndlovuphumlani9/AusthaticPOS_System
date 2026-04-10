/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employeeReport.service;

import com.mycompany.carol_boutique.employeeReport.dao.TopSellingEmployeeDAO;
import com.mycompany.carol_boutique.employeeReport.model.TopSellingEmployee;
import java.util.List;

/**
 *
 * @author Acer
 */
public class TopSellingEmployeeServiceImpl implements TopSellingEmployeeService {
    
     private TopSellingEmployeeDAO employeeDAO;

    public TopSellingEmployeeServiceImpl(TopSellingEmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    @Override
    public List<TopSellingEmployee> getTopSellingEmployees(Integer storeId) {
        return employeeDAO.getTopSellingEmployees(storeId);
    }
    
}
