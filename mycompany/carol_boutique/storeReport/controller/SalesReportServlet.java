/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.controller;

import com.mycompany.carol_boutique.storeReport.dao.StoreReportDaoImpl;
import com.mycompany.carol_boutique.storeReport.service.StoreReportService;
import com.mycompany.carol_boutique.storeReport.service.StoreReportServiceImpl;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "SalesReportServlet", urlPatterns = {"/SalesReportServlet"})
public class SalesReportServlet extends HttpServlet {
    private StoreReportService storeReportService;

    public SalesReportServlet() {
        this.storeReportService = new StoreReportServiceImpl(new StoreReportDaoImpl());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        int storeId = Integer.parseInt(request.getParameter("storeId"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        
       
        List<SalesReport> salesReports = storeReportService.getMonthlySalesReport(storeId, month, year);
        
        request.setAttribute("salesReports", salesReports);
        RequestDispatcher dispatcher = request.getRequestDispatcher("salesReport.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
         int storeId = Integer.parseInt(request.getParameter("storeId"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        
        List<SalesReport> salesReports = storeReportService.getMonthlySalesReport(storeId, month, year);

        // Set attributes for the JSP
        request.setAttribute("salesReports", salesReports);

        // Forward to JSP
        forwardToReportPage(request, response);
    }
    
     private void forwardToReportPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("salesReport.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
