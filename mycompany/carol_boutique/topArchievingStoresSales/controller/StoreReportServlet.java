package com.mycompany.carol_boutique.topArchievingStoresSales.controller;

import com.mycompany.carol_boutique.topArchievingStoresSales.dao.StoreDaoImplTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.dao.StoreDaoTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.DailySales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.EmployeeSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.ProductSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.StoreTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.TopSalesPerson;
import com.mycompany.carol_boutique.topArchievingStoresSales.service.StoreServiceImplTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.service.StoreServiceTop;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/StoreReportServlet")
public class StoreReportServlet extends HttpServlet {
    private StoreDaoTop storeDaoTop = new StoreDaoImplTop();
    private StoreServiceTop storeServiceTop = new StoreServiceImplTop(storeDaoTop);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("reportType");

        if (reportType == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Report type is required");
            return;
        }

        switch (reportType) {
            case "topAchieving":
                handleTopAchievingStores(request, response);
                break;

            case "topRated":
                handleTopRatedStores(request, response);
                break;

            case "salesReport":
                handleSalesReport(request, response);
                break;
                
             case "topSellingTellers":
                handleTopSellingTellers(request, response);
                break;

            case "topSellingTellersByStore":
                handleTopSellingTellersByStore(request, response);
                break; 
             
             case "topSellingProducts":
                handleTopSellingProducts(request, response);
                break;  
              
             case "productSalesReport" :
                 handleProductSalesReport(request,response);
                break;
                
              case "dailySales" :
                 handleDailySalesReport(request,response);
                break;
                
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type");
                break;
        }
    }

    private void handleTopAchievingStores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreTop> topStores = storeServiceTop.getTopAchievingStores();
        request.setAttribute("topStores", topStores);
        request.getRequestDispatcher("topStoresReport.jsp").forward(request, response);
    }

    private void handleTopRatedStores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StoreTop> topStores = storeServiceTop.getTopRatedStores();
        request.setAttribute("topStores", topStores);
        request.getRequestDispatcher("topRatedStores.jsp").forward(request, response);
    }

    private void handleSalesReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
//            int storeId = Integer.parseInt(request.getParameter("storeId"));
//            int month = Integer.parseInt(request.getParameter("month"));
//            int year = Integer.parseInt(request.getParameter("year"));

//            List<StoreTop> storeSalesReport = storeServiceTop.getSalesReportForStore(storeId, month, year);
            List<StoreTop> storeSalesReport = storeServiceTop.getSalesReportForStore(10000,7,2024);

            request.setAttribute("storeSalesReport", storeSalesReport);
            request.getRequestDispatcher("topSalesReportForOneStore.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println("Error");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters");
        }
    }
    
    private void handleTopSellingTellers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EmployeeSales> topTellers = storeServiceTop.getTopSellingTellers();
        request.setAttribute("topTellers", topTellers);
        request.getRequestDispatcher("topSellingTellers.jsp").forward(request, response);
    }

    private void handleTopSellingTellersByStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
             int storeId=10000; // display a particular store/// 
             
            if(request.getParameter("storeId")!=null){
                storeId= Integer.parseInt(request.getParameter("storeId"));
                request.setAttribute("storeId", storeId);
            }

            List<EmployeeSales> topTellers = storeServiceTop.getTopSellingTellersByStore(storeId);
            request.setAttribute("topTellers", topTellers);
            request.getRequestDispatcher("topSellingTellersByStore.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid store ID");
        }
    }
    
     private void handleTopSellingProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductSales> topProducts = storeServiceTop.getTopSellingProducts();
        request.setAttribute("topProducts", topProducts);
        request.getRequestDispatcher("topSellingProducts.jsp").forward(request, response);
    }
     
     private void handleProductSalesReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = 1;//Integer.parseInt(request.getParameter("productId"));
            System.out.println("Product ID: " + productId); // Debug statement
            
            if(request.getParameter("productId")!=null){
                productId= Integer.parseInt(request.getParameter("productId"));
                request.setAttribute("productId", productId);
            }

            List<SalesReport> salesReport = storeServiceTop.getSalesReportByProduct(productId);
            TopSalesPerson topSalesPerson = storeServiceTop.getTopSalesPersonByProduct(productId);

            System.out.println("Sales Report Size: " + salesReport.size()); // Debug statement
            System.out.println("Top Sales Person: " + topSalesPerson); // Debug statement

            request.setAttribute("salesReport", salesReport);
            request.setAttribute("topSalesPerson", topSalesPerson);
            request.getRequestDispatcher("productSalesReport.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
     
     private void handleDailySalesReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        int storeId = 10000;//Integer.parseInt(request.getParameter("storeId"));
        
          if(request.getParameter("storeId")!=null){
                storeId= Integer.parseInt(request.getParameter("storeId"));
                request.setAttribute("storeId", storeId);
            }
        List<DailySales> dailySales = storeServiceTop.getDailySales(storeId);
        request.setAttribute("dailySales", dailySales);
        request.getRequestDispatcher("dailySales.jsp").forward(request, response);
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid store ID");
    }
}

}
