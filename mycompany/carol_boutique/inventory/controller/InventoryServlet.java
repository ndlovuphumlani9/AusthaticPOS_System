/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.carol_boutique.inventory.controller;

import com.google.gson.Gson;
import com.mycompany.carol_boutique.employee.model.Employee;
//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.inventory.dao.InventoryDaoImpl;
import com.mycompany.carol_boutique.inventory.service.InventoryService;
import com.mycompany.carol_boutique.inventory.service.InventoryServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mrqts
 */
@WebServlet(name = "InventoryServlet", urlPatterns = {"/InventoryServlet"})
public class InventoryServlet extends HttpServlet {
    InventoryService inventoryService = new InventoryServiceImpl(new InventoryDaoImpl());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getParameter("submit")) {
            case "stock" :
                Employee employee = (Employee) request.getSession(false).getAttribute("employee");
                List<Stock> stock = inventoryService.lowStock(employee);
                request.setAttribute("stock", stock);
                request.getRequestDispatcher("lowstock.jsp").forward(request, response);
                break;
            case "all" :
                
                break;                       
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch(request.getParameter("submit")) {
            case "fetchItemDetails":
                String barcode = request.getParameter("barcode");
                String[] barcodeParts = barcode.split("-");
                System.out.println("sending to dao");
                Item item = inventoryService.getItemByProductId(Integer.parseInt(barcodeParts[0]));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                if (item != null) {
                    item.setColour(barcodeParts[2]);
                    item.setSize(barcodeParts[1]);
                    item.setBarcode(barcode);
                    System.out.println("created item");
                    System.out.println(item.getName());
                    System.out.println(item.getBarcode());
                    System.out.println(item.getPrice());
                    System.out.println(item.getDescription());
                    Gson gson = new Gson();
                    String itemJson = gson.toJson(item);
                    out.print(itemJson);
                } else {
                    String errorJson = "{\"error\": \"Item not found for barcode: " + barcode + "\"}";
                    out.print(errorJson);
                    out.flush();
                }
                break;
            case "process" :
                String[] barcodes2 = request.getParameterValues("barcode[]");
                String[] names = request.getParameterValues("name[]");
                String[] descriptions = request.getParameterValues("description[]");
                String[] priceStrings = request.getParameterValues("price[]");
                List<Item> items = new ArrayList<>();
                for (int i = 0; i < barcodes2.length; i++) {
                    String[] splitPrices = priceStrings[i].split(",");
                    String[] splitBarcodes = barcodes2[i].split(",");
                    String[] splitDescriptions = descriptions[i].split(",");
                    String[] splitNames = names[i].split(",");
                    for (int j = 0; j < splitPrices.length; j++) {
                        try {
                            NumberFormat format = NumberFormat.getInstance(Locale.US);
                            double price = format.parse(splitPrices[j].trim()).doubleValue();

                            Item item2 = new Item();
                            item2.setBarcode(splitBarcodes[j]);
                            item2.setName(splitNames[j]);
                            item2.setDescription(splitDescriptions[j]);
                            item2.setPrice(price);

                            items.add(item2);
                            System.out.println(item2);
                        } catch (java.text.ParseException e) {
                            System.err.println("Error parsing price: " + splitPrices[j]);
                        }
                    } 
                    System.out.println("in the for loop");
                }
                System.out.println("sending list to dao");
                Employee employee = (Employee) request.getSession(false).getAttribute("employee");
                inventoryService.addToInventory(items, employee);
                break;
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
