/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.customer.controller;

import com.mycompany.carol_boutique.customer.dao.CustomerDaoImpl;
import com.mycompany.carol_boutique.customer.exception.EmailExistsException;
import com.mycompany.carol_boutique.customer.model.Method;
import com.mycompany.carol_boutique.customer.model.Review;
import com.mycompany.carol_boutique.customer.service.CustomerService;
import com.mycompany.carol_boutique.customer.service.CustomerServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mrqts
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {"/CustomerServlet"})
public class CustomerServlet extends HttpServlet {
    CustomerService customerService = new CustomerServiceImpl(new CustomerDaoImpl());

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
            case "Submit Review":
            try {
                String name = request.getParameter("name");
                String number = request.getParameter("number");
                String email = request.getParameter("email");
                int rating = Integer.parseInt(request.getParameter("rating"));
                String review = request.getParameter("review");
                int storeId = Integer.parseInt(request.getParameter("storeId"));
                String subscriptionTypeParam = request.getParameter("subscriptionType");
                Method method = Method.valueOf(subscriptionTypeParam != null ? subscriptionTypeParam : "NONE");              
                System.out.println("storeId: " + storeId);
                System.out.println("here");               
                String subscribeParam = request.getParameter("subscribe");
                boolean isSubscribed = subscribeParam != null && subscribeParam.equals("on");               
                customerService.addReview(new Review(name, number, email, rating, review, storeId));                
                if (isSubscribed) {
                    customerService.subscribe(name, number, email, method);
                    System.out.println("User opted to subscribe to the newsletter.");
                }               
            } catch (EmailExistsException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            break;

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
