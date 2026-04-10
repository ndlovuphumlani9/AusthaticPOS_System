/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employee.model.Role;
//import com.mycompany.carol_boutique.employ.model.Employee;
//import com.mycompany.carol_boutique.employ.model.Role;
import com.mycompany.carol_boutique.ibt.dao.IBTDaoImpl;
import com.mycompany.carol_boutique.ibt.model.IBT;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.ibt.model.Store;
import com.mycompany.carol_boutique.ibt.service.IBTService;
import com.mycompany.carol_boutique.ibt.service.IBTServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mrqts
 */
@WebServlet(name = "IBTServlet", urlPatterns = {"/IBTServlet"})
public class IBTServlet extends HttpServlet {
    IBTService ibtService = new IBTServiceImpl(new IBTDaoImpl());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getParameter("submit")) {
            case "all" :
                Employee employee = (Employee) request.getSession(false).getAttribute("employee");
                List<IBT> ibts = ibtService.allIBTs(employee.getStoreId());
                request.setAttribute("ibts", ibts);
                request.getRequestDispatcher("allIBTs.jsp").forward(request, response);
                break;
            case "stores" :
                List<Store> stores = ibtService.allStores();
                request.setAttribute("stores", stores);
                request.getRequestDispatcher("allStores.jsp").forward(request, response);
                break;
            case "manage" :
                Employee manager = (Employee) request.getSession(false).getAttribute("employee");
                if (manager.getRole() == Role.MANAGER) {
                    List<IBT> pending = ibtService.pending(manager.getStoreId());
                    request.setAttribute("pending", pending);
                    request.getRequestDispatcher("manageIBTs.jsp").forward(request, response);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getParameter("submit")) {
            case "fetchItemDetails":
                String barcode = request.getParameter("barcode");
                String size = request.getParameter("size");
                String color = request.getParameter("color");              
                String quantityStr = request.getParameter("quantity");
                HttpSession session2 = request.getSession(true);
                session2.setAttribute("quantity", quantityStr);
                String[] barcodeParts = barcode.split("-");
                StringBuilder sb = new StringBuilder();
                sb.append(barcodeParts[0]).append("-").append(size).append("-").append(color);
                System.out.println(sb);

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityStr);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity");
                    return;
                }

                List<Stock> stores = ibtService.companyAvailability(sb.toString(), quantity);
                Gson gson = new Gson();
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("barcode", barcode);
                jsonResponse.add("stores", gson.toJsonTree(stores)); // Serialize list of stocks

                try (PrintWriter out = response.getWriter()) {
                    gson.toJson(jsonResponse, out); // Write JSON to response
                } 
                break;
            case "processRow" :
                // Retrieve the details sent from the client
                String barcode2 = request.getParameter("barcode");
                String color2 = request.getParameter("color");
                String quantityStr2 = request.getParameter("quantity");
                String size2 = request.getParameter("size");
                String store = request.getParameter("store");
                int storeId = Integer.parseInt(request.getParameter("storeId"));
                Stock stock = new Stock(storeId, store, barcode2, Integer.parseInt((String)request.getSession(false).getAttribute("quantity")), color2, size2);
                System.out.println("yoh");
                System.out.println(stock);
                HttpSession session3 = request.getSession(true);
                session3.setAttribute("stock", stock);

                Employee employee = (Employee) request.getSession(false).getAttribute("employee");
                String address = ibtService.storeAddress(employee.getStoreId());
                request.setAttribute("address", address);
                request.getRequestDispatcher("ibtpage.jsp").forward(request, response);
                break;
            case "Request" :
                Stock stock2 = (Stock) request.getSession(false).getAttribute("stock");
                String customer = request.getParameter("customer");
                Employee employee2 = (Employee) request.getSession(false).getAttribute("employee");
                ibtService.requestIBT(stock2, employee2, customer);
                doGet(request, response);
                break;
            case "Approve" :
                int ibtId = Integer.parseInt(request.getParameter("id"));
                ibtService.approve(ibtId);
                Employee manager = (Employee) request.getSession(false).getAttribute("employee");
                List<IBT> pending = ibtService.pending(manager.getStoreId());
                request.setAttribute("pending", pending);
                request.getRequestDispatcher("manageIBTs.jsp").forward(request, response);
                break;
            case "Delete" :
                int ibtIdDelete = Integer.parseInt(request.getParameter("id"));
                ibtService.cancel(ibtIdDelete);
                Employee employeeDelete = (Employee) request.getSession(false).getAttribute("employee");
                List<IBT> ibts = ibtService.allIBTs(employeeDelete.getStoreId());
                request.setAttribute("ibts", ibts);
                request.getRequestDispatcher("allIBTs.jsp").forward(request, response);
                break;
            case "Received" :
                int ibtIdDelivered = Integer.parseInt(request.getParameter("id"));
                ibtService.cancel(ibtIdDelivered);
                Employee employeeDelivered = (Employee) request.getSession(false).getAttribute("employee");
                List<IBT> ibtsUpdate = ibtService.allIBTs(employeeDelivered.getStoreId());
                request.setAttribute("ibts", ibtsUpdate);
                request.getRequestDispatcher("allIBTs.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
