/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.controller;

import com.mycompany.carol_boutique.ibt.service.SMSService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Acer
 */
@WebServlet(name = "SendSMSSServlet", urlPatterns = {"/SendSMSSServlet"})
public class SendSMSSServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

    private SMSService smsService;

    public SendSMSSServlet() {
        this.smsService = new SMSService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String messageText = "POS System !!!! 🤣🤣🤣🤣"
                + "Testing the SMS API🤣🤣🤣";
        

        boolean isSent = smsService.sendSMS(phoneNumber, messageText);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (isSent) {
            out.println("<h2>Message sent successfully to " + phoneNumber + "</h2>");
        } else {
            out.println("<h2>Failed to send message. Please try again.</h2>");
        }
        out.close();
    }

}
