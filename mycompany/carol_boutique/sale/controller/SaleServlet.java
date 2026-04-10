/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.sale.controller;

import com.mycompany.carol_boutique.sale.dao.SaleDaoImpl;
import com.mycompany.carol_boutique.sale.service.SaleService;
import com.mycompany.carol_boutique.sale.service.SaleServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mrqts
 */
@WebServlet(name = "SaleServlet", urlPatterns = {"/SaleServlet"})
public class SaleServlet extends HttpServlet {
    SaleService saleService = new SaleServiceImpl(new SaleDaoImpl());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
