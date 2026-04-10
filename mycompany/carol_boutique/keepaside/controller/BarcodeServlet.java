/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.controller;

import com.mycompany.carol_boutique.keepaside.utilities.BarcodeGenerator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mrqts
 */
@WebServlet(name = "BarcodeServlet", urlPatterns = {"/BarcodeServlet"})
public class BarcodeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String barcode = request.getParameter("barcode");
            String[] values = barcode.split("-");
            BufferedImage barcodeImage = BarcodeGenerator.generateBarcode(values[0], values[1], values[1]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(barcodeImage, "png", baos);
            byte[] barcodeBytes = baos.toByteArray();
            response.setContentType("image/png");
            response.getOutputStream().write(barcodeBytes);
        } catch (Exception ex) {
            Logger.getLogger(BarcodeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getParameter("submit")) {
            case "Generate" :
                String id = request.getParameter("productId");
                String size = request.getParameter("size");
                //BufferedImage barcode = 
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
