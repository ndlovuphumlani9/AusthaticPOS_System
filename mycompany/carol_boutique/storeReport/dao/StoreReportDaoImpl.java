/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.dao;

import com.mycompany.carol_boutique.dbconnection.DbConnection;
import com.mycompany.carol_boutique.storeReport.model.LeastPerformingStore;
import com.mycompany.carol_boutique.storeReport.model.StoreReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class StoreReportDaoImpl implements StoreReportDao{
    private DbConnection dConnection;
    @Override
    public List<StoreReport> getStoresAchievingTarget(String monthYear) 
    {
        List<StoreReport> storeReports = new ArrayList<>();
         String query = "SELECT s.storeId, s.storeName, s.location, st.targetAmount, IFNULL(SUM(ds.totalSales), 0) AS totalSales " +
                       "FROM store s " +
                       "JOIN storetargets st ON s.storeId = st.storeId " +
                       "LEFT JOIN dailysales ds ON s.storeId = ds.storeId AND DATE_FORMAT(ds.salesDate, '%Y-%m') = DATE_FORMAT(st.targetMonth, '%Y-%m') " +
                       "WHERE DATE_FORMAT(st.targetMonth, '%Y-%m') = ? " +
                       "GROUP BY s.storeId, s.storeName, s.location, st.targetAmount " +
                       "HAVING totalSales >= st.targetAmount";
        try (Connection conn = dConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, monthYear);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StoreReport report = new StoreReport();
                report.setStoreId(rs.getInt("storeId"));
                report.setStoreName(rs.getString("storeName"));
                report.setLocation(rs.getString("location"));
                report.setTargetAmount(rs.getBigDecimal("targetAmount"));
                report.setTotalSales(rs.getBigDecimal("totalSales"));
                storeReports.add(report);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return storeReports;
    }

    @Override
    public List<LeastPerformingStore> getLeastPerformingStores(LocalDate startDate3Months, LocalDate endDate3Months, LocalDate startDate6Months, LocalDate endDate6Months) {
        List<LeastPerformingStore> stores = new ArrayList<>();
            String query = "SELECT storeId, storeName, " +
                           "AVG(CASE WHEN saleDate BETWEEN ? AND ? THEN totalSales ELSE NULL END) AS avgSales3Months, " +
                           "AVG(CASE WHEN saleDate BETWEEN ? AND ? THEN totalSales ELSE NULL END) AS avgSales6Months " +
                           "FROM sales s " +
                           "JOIN store st ON s.storeId = st.storeId " +
                           "GROUP BY s.storeId, st.storeName " +
                           "ORDER BY avgSales6Months ASC, avgSales3Months ASC";

            try (Connection conn = dConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setDate(1, Date.valueOf(startDate3Months));
                ps.setDate(2, Date.valueOf(endDate3Months));
                ps.setDate(3, Date.valueOf(startDate6Months));
                ps.setDate(4, Date.valueOf(endDate6Months));

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        LeastPerformingStore store = new LeastPerformingStore();
                        store.setStoreId(rs.getInt("storeId"));
                        store.setStoreName(rs.getString("storeName"));
                        store.setAverageSales3Months(rs.getDouble("avgSales3Months"));
                        store.setAverageSales6Months(rs.getDouble("avgSales6Months"));
                        stores.add(store);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception as needed
            }
            return stores;
    }

    @Override
    public List<SalesReport> getMonthlySalesReport(int storeId, int year, int month)
    {
        List<SalesReport> salesReports = new ArrayList<>();
    String query = "SELECT sales.saleId, sales.employeeId, sales.customerId, sales.totalAmount, sales.storeId, sales.saleTimestamp "
                 + "FROM sales "
                 + "WHERE sales.storeId = ? "
                 + "AND MONTH(sales.saleTimestamp) = ? "
                 + "AND YEAR(sales.saleTimestamp) = ?";

    try (Connection conn = dConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, storeId);
        stmt.setInt(2, month);
        stmt.setInt(3, year);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SalesReport report = new SalesReport();
            report.setSaleId(rs.getInt("saleId"));
            report.setEmployeeId(rs.getInt("employeeId"));
            report.setCustomerId(rs.getInt("customerId"));
            report.setTotalAmount(rs.getDouble("totalAmount"));
            report.setStoreId(rs.getInt("storeId"));
            report.setSalesDate(rs.getTimestamp("saleTimestamp"));
            salesReports.add(report);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return salesReports;
    }

}
