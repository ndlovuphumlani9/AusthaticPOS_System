/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.productReport.dao;

import com.mycompany.carol_boutique.dbconnection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class SalesReportDAOImpl implements SalesReportDAO {
     private DbConnection dConnection;

  
    @Override
    public List<Map<String, Object>> getTopSellingProducts(int limit) {
        List<Map<String, Object>> products = new ArrayList<>();
        String query = "SELECT p.productName, SUM(si.quantity) AS totalQuantity " +
                     "FROM saleitems si " +
                     "JOIN product p ON si.productId = p.productId " +
                     "GROUP BY si.productId " +
                     "ORDER BY totalQuantity DESC " +
                     "LIMIT ?";
        try (Connection conn = dConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("productName", rs.getString("productName"));
                product.put("totalQuantity", rs.getInt("totalQuantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Map<String, Object> getTopSellingStore() {
        Map<String, Object> store = new HashMap<>();
        String query = "SELECT s.storeName, SUM(s.totalAmount) AS totalSales " +
                     "FROM sales s " +
                     "JOIN store st ON s.storeId = st.storeId " +
                     "GROUP BY s.storeId " +
                     "ORDER BY totalSales DESC " +
                     "LIMIT 1";
        try (Connection conn = dConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
             ResultSet rs = stmt.executeQuery(query); 
            if (rs.next()) {
                store.put("storeName", rs.getString("storeName"));
                store.put("totalSales", rs.getBigDecimal("totalSales"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }
    
}
