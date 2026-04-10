package com.mycompany.carol_boutique.product.controller;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.product.utilities.BarcodeGenerator;
import com.mycompany.carol_boutique.ibt.model.Item;
import static com.mycompany.carol_boutique.ibt.utilities.Receipt.sendEmail;
import com.mycompany.carol_boutique.keepaside.controller.KeepAsideServlet;
import static com.mycompany.carol_boutique.keepaside.email.SendEmail.sendEmail;
import com.mycompany.carol_boutique.keepaside.exception.EmailNotSentException;
import com.mycompany.carol_boutique.product.dao.ProductDaoImpl;
import com.mycompany.carol_boutique.product.model.Barcode;
import com.mycompany.carol_boutique.product.model.Category;
import com.mycompany.carol_boutique.product.model.Product;
import com.mycompany.carol_boutique.product.model.ProductType;
import com.mycompany.carol_boutique.product.service.ProductService;
import com.mycompany.carol_boutique.product.service.ProductServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProductServlet", urlPatterns = {"/ProductServlet"})
public class ProductServlet extends HttpServlet {

    ProductService productService = new ProductServiceImpl(new ProductDaoImpl());

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
        String action = request.getParameter("action");

        if (null != action) {
            switch (action) {
                case "fetchItemDetails":
                    String barcode = request.getParameter("barcode");
                    Item item = productService.getItemByBarcode(barcode);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    if (item != null) {
                        Gson gson = new Gson();
                        String itemJson = gson.toJson(item);
                        out.print(itemJson);
                    } else {
                        String errorJson = "{\"error\": \"Item not found for barcode: " + barcode + "\"}";
                        out.print(errorJson);
                    }
                    out.flush();
                    break;
                case "keepAside":
                    String[] barcodes = request.getParameterValues("barcode[]");
                    String email = request.getParameter("email");
                    System.out.println("Barcode String: " + barcodes);
                    System.out.println("Customer Email: " + email);
                    System.out.println("Handling keep aside request");
                    Employee employee = (Employee) request.getSession(false).getAttribute("employee");
                    List<String> products = new ArrayList<>();
                    for (String barcode2 : barcodes) {
                        boolean added = productService.addKeepAside(barcode2.trim(), email, employee);
                        if (added) {
                            products.add(barcode2);
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
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                    break;
                case "process":
                    String[] barcodes2 = request.getParameterValues("barcode[]");
                    String[] names = request.getParameterValues("name[]");
                    String[] descriptions = request.getParameterValues("description[]");
                    String[] priceStrings = request.getParameterValues("price[]");
                    String email2 = request.getParameter("email");
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
                    }
                    double total = Double.parseDouble(request.getParameter("total-amount"));
                    HttpSession session1 = request.getSession(true);
                    session1.setAttribute("items", items);
                    session1.setAttribute("total", total);
                    session1.setAttribute("email", email2);

                    request.getRequestDispatcher("pay.jsp").forward(request, response);
                    break;
                case "pay":
                    Employee employee3 = (Employee) request.getSession(false).getAttribute("employee");
                    List<Item> items2 = (List<Item>) request.getSession(false).getAttribute("items");
                    double total2 = (Double) request.getSession(false).getAttribute("total");
                    String email3 = (String) request.getSession(false).getAttribute("email");

                    long cardNo = new Random().nextLong();
                    int saleId = 0;
                    saleId = productService.process(items2, employee3, total2, email3, cardNo);

                    sendEmail(email3, productService.storeAddress(employee3.getStoreId()), total2, items2, saleId, employee3.getStoreId());
                    break;
                case "authenticate":
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    Employee manager = productService.manager(username, password);
                    if (manager != null) {
                        HttpSession session4 = request.getSession(true);
                        session4.setAttribute("manager", manager);
                        response.getWriter().write("true");
                    } else {
                        response.getWriter().write("false");
                    }
                    break;
                case "Generate":
                    String name = request.getParameter("productName");
                    Category category = Category.valueOf(request.getParameter("category"));
                    long price = Long.parseLong(request.getParameter("price"));
                    String description = request.getParameter("description");
                    ProductType prodtype = ProductType.valueOf(request.getParameter("productType"));
                    System.out.println("here");
                    int productId = productService.addProduct(new Product(name, category, price, description, prodtype));

                    if (prodtype == ProductType.FOOTWEAR) {
                        String[] sizes = {"3", "4", "5", "6", "7", "8", "9"};
                        String[] colours = {"Brown", "Blue", "Green", "Black", "White"};
                        try {
                            for (int i = 0; i < sizes.length; i++) {
                                for (int j = 0; j < colours.length; j++) {
                                    Barcode barcodez = productService.generateBarcode(productId, sizes[i], colours[j]);
                                }
                            }
                        } catch (WriterException ex) {
                            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL"};
                        String[] colours = {"Brown", "Blue", "Green", "Black", "White"};
                        try {
                            for (int i = 0; i < sizes.length; i++) {
                                for (int j = 0; j < colours.length; j++) {
                                    Barcode barcodez = productService.generateBarcode(productId, sizes[i], colours[j]);
                                }
                            }
                        } catch (WriterException ex) {
                            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    request.getRequestDispatcher("dashboard.jsp").forward(request, response);
                    break;
                case "Buy":
                    String ibtId4 = request.getParameter("ibtId");
                    String barcode2 = request.getParameter("barcode");
                    String storeFrom2 = request.getParameter("storeFrom");
                    Item item2 = productService.getItemByBarcode(barcode2);
                    request.setAttribute("ibtId", ibtId4);
                    request.setAttribute("barcode", barcode2);
                    request.setAttribute("storeFrom", storeFrom2);
                    request.setAttribute("item", item2);
                    // Store the item in session to maintain the state
                    HttpSession session = request.getSession();
                    List<Item> cart = (List<Item>) session.getAttribute("cart");
                    if (cart == null) {
                        cart = new ArrayList<>();
                    }
                    cart.add(item2);
                    session.setAttribute("cart", cart);

                    request.getRequestDispatcher("sale.jsp").forward(request, response);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private double calculateTotal(List<Item> scannedItems) {
        double total = 0.0;
        for (Item item : scannedItems) {
            total += item.getPrice();
        }
        return total;
    }
}
