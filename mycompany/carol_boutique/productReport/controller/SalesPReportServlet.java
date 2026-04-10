/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.productReport.controller;

import com.mycompany.carol_boutique.productReport.dao.SalesReportDAO;
import com.mycompany.carol_boutique.productReport.dao.SalesReportDAOImpl;
import com.mycompany.carol_boutique.productReport.service.SalesReportService;
import com.mycompany.carol_boutique.productReport.service.SalesReportServiceImpl;
import com.mycompany.carol_boutique.storeReport.dao.StoreReportDaoImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
@WebServlet(name = "SalesPReportServlet", urlPatterns = {"/SalesPReportServlet"})
public class SalesPReportServlet extends HttpServlet {
    
    private SalesReportService salesReportService;
    
    public SalesPReportServlet() {
        this.salesReportService = new SalesReportServiceImpl(new StoreReportDaoImpl());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        List<Map<String, Object>> topSellingProducts = salesReportService.getTopSellingProducts(40);
        Map<String, Object> topSellingStore = salesReportService.getTopSellingStore();

        request.setAttribute("topSellingProducts", topSellingProducts);
        request.setAttribute("topSellingStore", topSellingStore);

        RequestDispatcher dispatcher = request.getRequestDispatcher("salesPReport.jsp");
        dispatcher.forward(request, response);
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
