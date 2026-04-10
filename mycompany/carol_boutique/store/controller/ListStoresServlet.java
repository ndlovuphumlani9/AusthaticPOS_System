/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.store.controller;

import com.mycompany.carol_boutique.employee.dao.EmployeeDao;
import com.mycompany.carol_boutique.employee.dao.EmployeeDaoImpl;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employee.model.Role;
import com.mycompany.carol_boutique.store.dao.StoreDao;
import com.mycompany.carol_boutique.store.dao.StoreDaoImpl;
import com.mycompany.carol_boutique.store.model.Store;
import com.mycompany.carol_boutique.store.service.StoreService;
import com.mycompany.carol_boutique.store.service.StoreServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
@WebServlet(name = "ListStoresServlet", urlPatterns = {"/ListStoresServlet"})
public class ListStoresServlet extends HttpServlet {

     private StoreDao storeDao;
    private StoreService storeService;

    @Override
    public void init() throws ServletException {
        super.init();
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        StoreDao storeDao = new StoreDaoImpl();
        storeDao.setEmployeeDao(employeeDao);
        this.storeService = new StoreServiceImpl(new StoreDaoImpl());
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        List<Store> stores = storeService.getAllStores();
        // Set the list as a request attribute
        request.setAttribute("stores", stores);
        // Forward the request to listStores.jsp
        Employee employee= (Employee)request.getSession(false).getAttribute("employee");
        if(Role.MANAGER == employee.getRole()){
             request.getRequestDispatcher("listStores.jsp").forward(request, response);
        }else if(Role.ADMIN == employee.getRole()){
            request.getRequestDispatcher("manageStores.jsp").forward(request, response);

        }
                
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
