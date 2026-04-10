/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.store.controller;

import com.mycompany.carol_boutique.employee.dao.EmployeeDao;
import com.mycompany.carol_boutique.employee.dao.EmployeeDaoImpl;
import com.mycompany.carol_boutique.employee.service.EmployeeServiceImpl;
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
 * @author Poloko
 */
@WebServlet(name = "StoreServlet", urlPatterns = {"/StoreServlet"})
public class StoreServlet extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "addStore":
                    handleAddStore(request, response);
                    break;
                case "update":
                    updateStore(request, response);
                    break;
                case "delete":
                    deleteStore(request, response);
                    break;
                default:
                    response.sendRedirect("dashboard.jsp");
                    break;
            }
        } else {
            response.sendRedirect("dashboard.jsp");
        }
    }

    private void handleAddStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("location");
        String storePhone = request.getParameter("storePhone");
        String storeEmail = request.getParameter("storeEmail");

        if (location == null || location.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Store name and location are required.");
            request.getRequestDispatcher("addStore.jsp").forward(request, response);
            return;
        }

        Store store = new Store(location,  storePhone, storeEmail);

        boolean success = storeService.addStore(store);

        if (success) {
            request.setAttribute("successMessage", "Store successfully added.");
        } else {
            request.setAttribute("errorMessage", "Failed to add store. Manager may not exist.");
        }
        request.getRequestDispatcher("addStore.jsp").forward(request, response);
    }

    private void updateStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        int storeId = Integer.parseInt(request.getParameter("storeId"));
        String location = request.getParameter("location");
        String storePhone = request.getParameter("storePhone");
        String storeEmail = request.getParameter("storeEmail");

        Store store = new Store();
        store.setStoreId(storeId);
        store.setLocation(location);
        store.setStorePhone(storePhone);
        store.setStoreEmail(storeEmail);

        boolean isUpdated = storeService.updateStore(store);

        if (isUpdated) {
            request.setAttribute("message", "Store details updated successfully.");
        } else {
            request.setAttribute("message", "Failed to update store details.");
        }

        request.getRequestDispatcher("updateStore.jsp").forward(request, response);
    
        
        
        
//        String storeIdParam = request.getParameter("storeId");
//        String location = request.getParameter("location");
//        String storePhone = request.getParameter("storePhone");
//        String storeEmail = request.getParameter("storeEmail");
//
//        int storeId, managerId = 0;
//
//        try {
//            storeId = Integer.parseInt(storeIdParam);
//        } catch (NumberFormatException e) {
//            request.setAttribute("errorMessage", "Store ID must be a number.");
//            request.getRequestDispatcher("updateStore.jsp").forward(request, response);
//            return;
//        }
//
//        
//
//        if (location == null || location.trim().isEmpty()) {
//            request.setAttribute("errorMessage", "Store name and location are required.");
//            request.getRequestDispatcher("updateStore.jsp").forward(request, response);
//            return;
//        }
//
//        Store store = new Store(location,  storePhone, storeEmail);
//        store.setStoreId(storeId);
//
//        boolean isUpdated = storeService.updateStore(store);
//
//        if (isUpdated) {
//            request.setAttribute("successMessage", "Store successfully updated.");
//            request.getRequestDispatcher("updateStore.jsp").forward(request, response);
//        } else {
//            request.setAttribute("errorMessage", "Failed to update store. Please try again.");
//            request.getRequestDispatcher("updateStore.jsp").forward(request, response);
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Store> stores = storeService.getAllStores(); // Add this method in StoreService
        request.setAttribute("stores", stores);
//        request.getRequestDispatcher("listStores.jsp").forward(request, response);
        request.getRequestDispatcher("manageStores.jsp").forward(request, response);
        
        int storeId = Integer.parseInt(request.getParameter("storeId"));
        Store store = storeService.getStoreById(storeId);

        if (store != null) {
            request.setAttribute("store", store);
            request.getRequestDispatcher("updateStore.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Store not found.");
            request.getRequestDispatcher("updateStore.jsp").forward(request, response);
        }
    }

    private void deleteStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String storeIdParam = request.getParameter("storeId");

        int storeId;
        try {
            storeId = Integer.parseInt(storeIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Store ID must be a number.");
            request.getRequestDispatcher("manageStores.jsp").forward(request, response);
            return;
        }

        boolean isDeleted = storeService.deleteStore(storeId);

        if (isDeleted) {
            request.setAttribute("successMessage", "Store successfully deleted.");
            request.getRequestDispatcher("manageStores.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to delete store. Please try again.");
        }
        
    }

}
