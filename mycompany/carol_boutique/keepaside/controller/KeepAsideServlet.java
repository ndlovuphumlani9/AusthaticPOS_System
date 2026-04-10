/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.controller;

import com.google.gson.Gson;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.keepaside.dao.KeepAsideDaoImpl;
import static com.mycompany.carol_boutique.keepaside.email.SendEmail.sendEmail;
import com.mycompany.carol_boutique.keepaside.exception.EmailNotSentException;
import com.mycompany.carol_boutique.keepaside.model.KeepAside;
import com.mycompany.carol_boutique.keepaside.model.Status;
import com.mycompany.carol_boutique.keepaside.service.KeepAsideService;
import com.mycompany.carol_boutique.keepaside.service.KeepAsideServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Mrqts
 */
@WebServlet(name = "KeepAsideServlet", urlPatterns = {"/KeepAsideServlet"})
public class KeepAsideServlet extends HttpServlet {
    KeepAsideService keepAsideService = new KeepAsideServiceImpl(new KeepAsideDaoImpl());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employee employee = (Employee) request.getSession(false).getAttribute("employee");
        List<KeepAside> keepasides = keepAsideService.allKeepAside(employee.getStoreId());
        System.out.println("Number of KeepAsides fetched: " + (keepasides != null ? keepasides.size() : 0));
        request.setAttribute("keepasides", keepasides);
        request.getRequestDispatcher("allKeepAside.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submitAction = request.getParameter("submit");

        if ("Keep Aside".equals(submitAction)) {
            Employee employee = (Employee) request.getSession(false).getAttribute("employee");
            String barcodeString = request.getParameter("barcodes");
            String email = request.getParameter("email");
            String[] barcodes = barcodeString.split(",");
            List<String> products = new ArrayList<>();

            for (String barcode : barcodes) {
                boolean added = keepAsideService.addKeepAside(barcode, email, employee);
                if (added) {
                    products.add(barcode);
                }
            }
            try {
                sendEmail(email, "You have 48 hours to collect your items.");
            } catch (EmailNotSentException ex) {
                Logger.getLogger(KeepAsideServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Gson gson = new Gson();
            String jsonProducts = gson.toJson(products);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonProducts);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
