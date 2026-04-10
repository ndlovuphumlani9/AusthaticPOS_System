/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.manager.dao;


import com.mycompany.carol_boutique.employee.model.Employee;
import java.util.List;

public interface TellerDao {
//    boolean addTeller(Employee teller);
//    Employee getTellerByUsername(int username);
    List<Employee> getAllTellers();
}
