/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.voicebutton.controller;

import com.mycompany.carol_boutique.voicebutton.service.VoiceService;
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
@WebServlet(name = "VoiceServlet", urlPatterns = {"/VoiceServlet"})
public class VoiceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VoiceService voiceService = new VoiceService();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
//        response.setContentType("text/plain");
//        try {
//            voiceService.speak("Next customer, please");
//            response.getWriter().write("Voice triggered.");
//        } catch (Exception e) {
//            response.getWriter().write("Error triggering voice: " + e.getMessage());
//        }
//    
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
