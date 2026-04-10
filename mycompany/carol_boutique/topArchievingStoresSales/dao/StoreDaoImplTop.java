/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.topArchievingStoresSales.dao;

import com.mycompany.carol_boutique.topArchievingStoresSales.model.DailySales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.EmployeeSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.ProductSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.StoreTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.TopSalesPerson;
import com.mycompany.carol_boutique.util.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Poloko
 */

public class StoreDaoImplTop implements StoreDaoTop {
    
    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER_NAME = "root";
    private final String PASSWORD = "MATHOthoana@20";
    private Connection conn;
    
   // DbPoolManager... search 
 

    public StoreDaoImplTop() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Could not make a connection in the store dao: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @Override
    public List<StoreTop> getTopAchievingStores() {
        List<StoreTop> topStores = new ArrayList<>();
        String query = "SELECT s.storeId, s.storeName, SUM(sa.totalAmount) AS totalSales " +
                       "FROM store s " +
                       "JOIN sales sa ON s.storeId = sa.storeId " +
                       "GROUP BY s.storeId, s.storeName " +
                       "ORDER BY totalSales DESC";
        try (
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                StoreTop store = new StoreTop();
                store.setStoreId(rs.getInt("storeId"));
                store.setStoreName(rs.getString("storeName"));
                store.setTotalSales(rs.getDouble("totalSales"));
                topStores.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topStores;
    }
    
     @Override
    public List<StoreTop> fetchTopRatedStores() {
        String query = "SELECT s.storeId, s.storeName, AVG(r.ratingValue) AS averageRating " +
                       "FROM store s " +
                       "JOIN rating r ON s.storeId = r.storeId " +
                       "GROUP BY s.storeId, s.storeName " +
                       "ORDER BY averageRating DESC";
        List<StoreTop> topStores = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                StoreTop store = new StoreTop();
                store.setStoreId(rs.getInt("storeId"));
                store.setStoreName(rs.getString("storeName"));
                store.setAverageRating(rs.getDouble("averageRating"));
                topStores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topStores;
    }
    
    public List<StoreTop> getSalesReportForStore(int storeId, int month, int year) {
    List<StoreTop> storeSalesReport = new ArrayList<>();
    String query = "SELECT sales.saleId, sales.employeeId, sales.customerId, sales.totalAmount, sales.storeId, sales.saleTimestamp "
                 + "FROM sales "
                 + "WHERE sales.storeId = ? "
                 + "AND MONTH(sales.saleTimestamp) = ? "
                 + "AND YEAR(sales.saleTimestamp) = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, storeId);
        stmt.setInt(2, month);
        stmt.setInt(3, year);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            StoreTop store = new StoreTop();
            store.setSaleId(rs.getInt("saleId"));
            store.setEmployeeId(rs.getInt("employeeId"));
            store.setCustomerId(rs.getInt("customerId"));
            store.setTotalAmount(rs.getBigDecimal("totalAmount"));
            store.setStoreId(rs.getInt("storeId"));
            store.setSalesDate(rs.getTimestamp("saleTimestamp"));
            storeSalesReport.add(store);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return storeSalesReport;
}
    
     @Override
    public List<EmployeeSales> getTopSellingTellers() {
        List<EmployeeSales> employeeSalesList = new ArrayList<>();
        String query = "SELECT e.employeeId, e.firstName, e.lastName, SUM(s.totalAmount) AS totalSales "
                     + "FROM employee e "
                     + "JOIN sales s ON e.employeeId = s.employeeId "
                     + "WHERE e.role = 'teller' "
                     + "GROUP BY e.employeeId, e.firstName, e.lastName "
                     + "ORDER BY totalSales DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EmployeeSales employee = new EmployeeSales();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setTotalSales(rs.getDouble("totalSales"));
                employeeSalesList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeSalesList;
    }

    @Override
    public List<EmployeeSales> getTopSellingTellersByStore(int storeId) {
        List<EmployeeSales> topTellers = new ArrayList<>();
        String query = "SELECT e.employeeId, e.firstName, e.lastName, SUM(s.totalAmount) AS totalSales " +
                       "FROM employee e " +
                       "JOIN sales s ON e.employeeId = s.employeeId " +
                       "WHERE e.storeId = ? AND e.role = 'teller' " +
                       "GROUP BY e.employeeId, e.firstName, e.lastName " +
                       "ORDER BY totalSales DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EmployeeSales employee = new EmployeeSales();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setTotalSales(rs.getDouble("totalSales"));
                topTellers.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topTellers;
    }
    
  public List<ProductSales> getTopSellingProducts() {
    List<ProductSales> topProducts = new ArrayList<>();
    String query = "SELECT p.productId, p.productName, SUM(si.quantity) AS totalQuantitySold " +
                   "FROM saleitems si " +
                   "JOIN product p ON si.productId = p.productId " +
                   "GROUP BY p.productId " +
                   "ORDER BY totalQuantitySold DESC " +
                   "LIMIT 40";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            ProductSales product = new ProductSales();
            product.setProductId(rs.getInt("productId"));
            product.setProductName(rs.getString("productName"));
            product.setTotalQuantitySold(rs.getInt("totalQuantitySold"));
            topProducts.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return topProducts;
}
  
  @Override
public List<SalesReport> getSalesReportByProduct(int productId) {
    List<SalesReport> salesReports = new ArrayList<>();
    String query = "SELECT s.saleId, si.productId, p.productName, si.quantity, si.price * si.quantity AS totalAmount, " +
                   "s.storeId, s.saleTimestamp " +
                   "FROM saleitems si " +
                   "JOIN sales s ON si.saleId = s.saleId " +
                   "JOIN product p ON si.productId = p.productId " +
                   "WHERE si.productId = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SalesReport report = new SalesReport();
            report.setSaleId(rs.getInt("saleId"));
            report.setProductId(rs.getInt("productId"));
            report.setProductName(rs.getString("productName"));
            report.setQuantitySold(rs.getInt("quantity"));
            report.setTotalAmount(rs.getDouble("totalAmount"));
            report.setStoreId(rs.getInt("storeId"));
            report.setSaleTimestamp(rs.getString("saleTimestamp"));
            salesReports.add(report);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return salesReports;
}

@Override
public TopSalesPerson getTopSalesPersonByProduct(int productId) {
    TopSalesPerson topSalesPerson = null;
    String query = "SELECT e.employeeId, e.firstName, e.lastName, SUM(si.quantity) AS totalQuantitySold " +
                   "FROM saleitems si " +
                   "JOIN sales s ON si.saleId = s.saleId " +
                   "JOIN employee e ON s.employeeId = e.employeeId " +
                   "WHERE si.productId = ? AND e.role = 'teller' " +
                   "GROUP BY e.employeeId, e.firstName, e.lastName " +
                   "ORDER BY totalQuantitySold DESC " +
                   "LIMIT 1";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            topSalesPerson = new TopSalesPerson();
            topSalesPerson.setEmployeeId(rs.getInt("employeeId"));
            topSalesPerson.setFirstName(rs.getString("firstName"));
            topSalesPerson.setLastName(rs.getString("lastName"));
            topSalesPerson.setTotalQuantitySold(rs.getInt("totalQuantitySold"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return topSalesPerson;
}
public List<DailySales> getDailySales(int storeId) {
    List<DailySales> dailySalesList = new ArrayList<>();
    String query = "SELECT s.saleId, si.productId, p.productName, si.quantity, si.price * si.quantity AS totalAmount, s.saleTimestamp " +
                   "FROM sales s " +
                   "JOIN saleitems si ON s.saleId = si.saleId " +
                   "JOIN product p ON si.productId = p.productId " +
                   "WHERE s.storeId = ? AND DATE(s.saleTimestamp) = CURDATE()";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, storeId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            DailySales sale = new DailySales();
            sale.setSaleId(rs.getInt("saleId"));
            sale.setProductId(rs.getInt("productId"));
            sale.setProductName(rs.getString("productName"));
            sale.setQuantitySold(rs.getInt("quantity"));
            sale.setTotalAmount(rs.getDouble("totalAmount"));
            sale.setSaleTimestamp(rs.getString("saleTimestamp"));
            dailySalesList.add(sale);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return dailySalesList;
}

}
