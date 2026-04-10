//package com.mycompany.carol_boutique.manager.controller;
//
//import com.mycompany.carol_boutique.employee.model.Employee;
//import com.mycompany.carol_boutique.manager.dao.TellerDaoImpl;
////import com.mycompany.carol_boutique.manager.model.Teller;
//import com.mycompany.carol_boutique.manager.service.TellerService;
//import com.mycompany.carol_boutique.manager.service.TellerServiceImpl;
//import com.mycompany.carol_boutique.util.GuestMail;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebServlet(name = "TellerServlet", urlPatterns = {"/TellerServlet"})
//public class TellerServlet extends HttpServlet {
//
//    private TellerService tellerService;
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        this.tellerService = new TellerServiceImpl(new TellerDaoImpl());
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//    }
//    
//    }
////        String action = request.getParameter("action");
////
////        if (action != null) {
////            switch (action) {
////                case "login":
////                    handleLogin(request, response);
////                    break;
////                case "addTeller":
////                    handleAddTeller(request, response);
////                    break;
////                default:
////                    response.sendRedirect("login.jsp");
////                    break;
////            }
////        } else {
////            response.sendRedirect("login.jsp");
////        }
////    }
////
////    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        int username = Integer.parseInt(request.getParameter("username"));
////        String password = request.getParameter("password");
////
////        Teller teller = tellerService.authenticate(username, password);
////
////        if (teller != null) {
////            HttpSession session = request.getSession();
////            session.setAttribute("teller", teller);
////
////            if ("teller".equals(teller.getRole())) {
////                response.sendRedirect("tellerDashboard.jsp");
////            } else if ("manager".equals(teller.getRole())) {
////                response.sendRedirect("managerDashboard.jsp");
////            } else {
////                response.sendRedirect("login.jsp");
////            }
////        } else {
////            response.sendRedirect("login.jsp");
////        }
////    }
//
////    private void handleAddTeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        String firstName = request.getParameter("firstName");
////        String lastName = request.getParameter("lastName");
////        String email = request.getParameter("email");
////        String phone = request.getParameter("phone");
////        String password = request.getParameter("password");
////        Employee employee = (Employee)request.getSession(false).getAttribute("employee");
////        
////        Integer storeId = employee.getStoreId();
////
////        String role = "teller";
////
////        Teller teller = new Teller(firstName, lastName, role, email, phone, password, storeId);
////
////        boolean success = tellerService.registerTeller(teller);
////        
////
////        if (success) {
////            String subject = "Position Notification";
////            String body = "Dear " + firstName + ",\n\nYour POS (Point Of System) "
////                    + "Teller station LogIn "
////                    + "password is as follows: " + password +". "
////                    + "Carol's Boutique Team";
////            GuestMail.sendMail(email, subject, body);
////
////            response.sendRedirect("managerDashboard.jsp");
////        } else {
////            response.sendRedirect("addTeller.jsp");
////        }
////    }
////}
//
