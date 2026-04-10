/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.returns.controller;

import com.google.gson.Gson;
import com.mycompany.carol_boutique.employee.model.Employee;
//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import static com.mycompany.carol_boutique.ibt.utilities.Receipt.sendEmail;
import com.mycompany.carol_boutique.returns.dao.ReturnDaoImpl;
import com.mycompany.carol_boutique.returns.model.Outcome;
import com.mycompany.carol_boutique.returns.model.Reason;
import com.mycompany.carol_boutique.returns.service.ReturnService;
import com.mycompany.carol_boutique.returns.service.ReturnServiceImpl;
import static com.mycompany.carol_boutique.returns.utilities.Email.sendRefundEmail;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "ReturnServlet", urlPatterns = {"/ReturnServlet"})
public class ReturnServlet extends HttpServlet {
    ReturnService returnService = new ReturnServiceImpl(new ReturnDaoImpl());

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
        switch (request.getParameter("submit")) {
            case "Get" :
                int saleId = Integer.parseInt(request.getParameter("saleId"));
                List<Item> items = returnService.returns(saleId);
                for (Item item : items) {
                    System.out.println(item);
                }
                HttpSession session2 = request.getSession(true);
                session2.setAttribute("items", items);
                String json = new Gson().toJson(items); 
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                break;
            case "return":
                Employee employee = (Employee) request.getSession(false).getAttribute("employee");
                String selectedItem = request.getParameter("selectedItemId");
                double price = Double.parseDouble(request.getParameter("selectedItemPrice"));
                Reason reason = Reason.valueOf(request.getParameter("reasons"));
                Outcome outcome = Outcome.valueOf(request.getParameter("outcomes"));

                if (outcome == Outcome.REFUND) {
                    System.out.println("Selected item for refund: " + selectedItem);
                    System.out.println("price: " +price);
                    String[] details = returnService.processReturn(Integer.parseInt(selectedItem), reason, employee);
                    sendRefundEmail(details[1], details[0]);
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                } else if (outcome == Outcome.EXCHANGE) {
                    System.out.println("Selected item for exchange: " + selectedItem);
                    System.out.println("price: " +price);
                    HttpSession session3 = request.getSession(true); 
                    session3.setAttribute("price", price);
                    HttpSession session4 = request.getSession(true);               
                    session4.setAttribute("selectedItem", selectedItem);
                    request.getRequestDispatcher("return2.jsp").forward(request, response);
                }
                break;

            case "process" :
                Employee employee2 = (Employee) request.getSession(false).getAttribute("employee");
                String barcode = request.getParameter("barcode");
                String email = request.getParameter("email");
                String saleIdReturn = (String) request.getSession(false).getAttribute("selectedItem");
                int updatedId = returnService.processExchange(Integer.parseInt(saleIdReturn), barcode);
                List<Item> updated = returnService.getReceipt(updatedId);
                String address = returnService.storeAddress(employee2.getStoreId());
                double total = 0;
                for (Item item : updated) {
                    total += item.getPrice();
                }               
                sendEmail(email, address, total, updated, Integer.parseInt(saleIdReturn), employee2.getStoreId());
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
