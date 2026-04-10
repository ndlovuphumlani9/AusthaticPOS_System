/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.controller;

import com.google.gson.Gson;
import com.mycompany.carol_boutique.storeReport.dao.StoreReportDaoImpl;
import com.mycompany.carol_boutique.storeReport.model.LeastPerformingStore;
import com.mycompany.carol_boutique.storeReport.model.StoreReport;
import com.mycompany.carol_boutique.storeReport.service.StoreReportService;
import com.mycompany.carol_boutique.storeReport.service.StoreReportServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@WebServlet(name = "StoreReportsServlet", urlPatterns = {"/StoreReportsServlet"})
public class StoreReportsServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private StoreReportService storeReportService;

    public StoreReportsServlet() {
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
        String monthYear = request.getParameter("monthYear");
        if (monthYear == null || monthYear.isEmpty()) {
            monthYear = LocalDate.now().format(DATE_FORMATTER);
        }

        LocalDate endDate = LocalDate.parse(monthYear + "-01", DATE_FORMATTER).withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDate startDate3Months = endDate.minusMonths(3);
        LocalDate startDate6Months = endDate.minusMonths(6);

        List<StoreReport> storeReports = storeReportService.getStoresAchievingTarget(monthYear);
        List<LeastPerformingStore> leastPerformingStores = storeReportService.getLeastPerformingStores(startDate3Months, endDate, startDate6Months, endDate);

        request.setAttribute("monthYear", monthYear);
        request.setAttribute("storeReports", storeReports);
        request.setAttribute("leastPerformingStores", leastPerformingStores);

        request.getRequestDispatcher("/storesAchievingTargetReport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String monthYear = request.getParameter("monthYear");
        LocalDate now = LocalDate.now();
        LocalDate startDate3Months = now.minusMonths(3).withDayOfMonth(1);
        LocalDate endDate3Months = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());
        LocalDate startDate6Months = now.minusMonths(6).withDayOfMonth(1);
        LocalDate endDate6Months = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());

        List<StoreReport> storeReports = storeReportService.getStoresAchievingTarget(monthYear);
        List<LeastPerformingStore> leastPerformingStores = storeReportService.getLeastPerformingStores(startDate3Months, endDate3Months, startDate6Months, endDate6Months);

        
        // Convert list to JSON
        Gson gson = new Gson();
        String storeReportsJson = gson.toJson(storeReports);
        String leastPerformingStoresJson = gson.toJson(leastPerformingStores);
        // Set JSON data as request attribute
        request.setAttribute("storeReportsJson", storeReportsJson);
        request.setAttribute("leastPerformingStoresJson", leastPerformingStoresJson);      
       
        request.setAttribute("storeReports", storeReports);
        request.setAttribute("leastPerformingStores", leastPerformingStores);
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("storesAchievingTargetReport.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
