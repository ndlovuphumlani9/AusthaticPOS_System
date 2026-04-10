/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employeeReport.contriller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.carol_boutique.employeeReport.dao.TopSellingEmployeeDAO;
import com.mycompany.carol_boutique.employeeReport.dao.TopSellingEmployeeDAOImpl;
import com.mycompany.carol_boutique.employeeReport.model.TopSellingEmployee;
import com.mycompany.carol_boutique.employeeReport.service.TopSellingEmployeeService;
import com.mycompany.carol_boutique.employeeReport.service.TopSellingEmployeeServiceImpl;
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
@WebServlet(name = "TopSellingEmployeeServlet", urlPatterns = {"/TopSellingEmployeeServlet"})
public class TopSellingEmployeeServlet extends HttpServlet {
     private TopSellingEmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        // Initialize DAO and Service without direct connection handling
        TopSellingEmployeeDAO employeeDAO = new TopSellingEmployeeDAOImpl();
        employeeService = new TopSellingEmployeeServiceImpl(employeeDAO);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        Integer storeId = Integer.parseInt(request.getParameter("storeId"));
        List<TopSellingEmployee> employees = employeeService.getTopSellingEmployees(storeId);

        // Convert list to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(employees);

        // Set response type and send JSON data
        response.setContentType("application/json");
        response.getWriter().write(json);
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
