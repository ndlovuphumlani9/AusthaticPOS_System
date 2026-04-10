/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employeeReport.dao;

import com.mycompany.carol_boutique.employeeReport.model.TopSellingEmployee;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface TopSellingEmployeeDAO {
    List<TopSellingEmployee> getTopSellingEmployees(Integer storeId);
}
