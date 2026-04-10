/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employee.dao;

import com.mycompany.carol_boutique.employee.model.Employee;
import java.util.List;

public interface EmployeeDao {
    boolean addEmployee(Employee employee);
    Employee getEmployeeByUsername(int username);
    Employee getEmployeeByEmail(String email);
    boolean updatePassword(int employeeId, String newPasswordHash);
    boolean addManager(Employee employee);
    List<Employee> getAllManagers();
    List<Employee> getAllTellers();
    boolean deleteManager(int employeeId); 
    boolean deleteTeller(int employeeId);
    boolean doesManagerExist(int managerId);
    boolean addTeller(Employee employee);
}
