/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter
@NoArgsConstructor@AllArgsConstructor
public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    Role role;
    private String email;
    private String phone;
    private int storeId;
    private String password;

    public Employee(String firstName, String lastName, Role role, String email, String phone, String password, Integer storeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.storeId = storeId;
        this.password = password;
    }

//    public Employee() {
//    }
//
//    public Employee(String firstName, String lastName, String password, String role, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.role = role;
//        this.email = email;
//    }
//
//    public Employee(String firstName, String lastName, String password, String role, String email, int storeId) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.role = role;
//        this.email = email;
//        this.storeId = storeId;
//    }
//
    public Employee(int employeeId, String firstName, String lastName, Role role, String email, int storeId, String password, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.role = role;
        this.email = email;
        this.storeId = storeId;
        this.password = password;
        this.phone = phone;
    }
//
//    public Employee(String firstName, String lastName, String password, String role, String email, String phone, int storeId) {
//       this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.role = role;
//        this.email = email;
//        this.storeId = storeId;
//    }
//
//    public int getEmployeeId() {
//        return employeeId;
//    }
//
//    
//
    /**
     * @return the storeId
     */
    public int getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

}
