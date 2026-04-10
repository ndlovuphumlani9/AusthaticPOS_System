/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.manager.service;

//import com.mycompany.carol_boutique.manager.model.Teller;
import com.mycompany.carol_boutique.employee.model.Employee;
import java.util.List;



public interface TellerService {
//    boolean registerTeller(Teller teller);
     List<Employee> getAllTellers();
//    Teller authenticate(int username, String password);
}
