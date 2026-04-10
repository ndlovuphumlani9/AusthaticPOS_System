/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employee.service;

import com.mycompany.carol_boutique.employee.model.Employee;
import java.util.List;

public interface EmployeeService {
    boolean registerEmployee(Employee employee);
    Employee authenticate(int username, String password);
    boolean resetPassword(String email);
    boolean verifyOtp(String email, String otpCode,Employee employee);
    boolean updatePassword(int employeeId, String newPassword);
    public boolean addManager(Employee employee);
    List<Employee> getAllManagers();
    List<Employee> getAllTellers();
    boolean deleteManager(int employeeId);
    String generateRandomPassword();
}

