/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employeeReport.dao;

import com.mycompany.carol_boutique.dbconnection.DbConnection;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employeeReport.model.TopSellingEmployee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class TopSellingEmployeeDAOImpl implements TopSellingEmployeeDAO {
     private DbConnection dConnection;
     
//      public TopSellingEmployeeDAOImpl(DbConnection dConnection) {
//        this.dConnection = dConnection;
//    }

    public TopSellingEmployeeDAOImpl() {
        this.dConnection = dConnection;
    }
    @Override
    public List<TopSellingEmployee> getTopSellingEmployees(Integer storeId) {
        List<TopSellingEmployee> employees = new ArrayList<>();
        String query = "SELECT e.employeeId, CONCAT(e.firstName, ' ', e.lastName) AS employeeName, "
                   + "SUM(s.totalAmount) AS totalSales "
                   + "FROM sales s "
                   + "JOIN employee e ON s.employeeId = e.employeeId "
                   + "WHERE s.storeId = ? "
                   + "GROUP BY e.employeeId "
                   + "ORDER BY totalSales DESC";

        try (Connection conn = dConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, storeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TopSellingEmployee employee = new TopSellingEmployee();
                    employee.setEmployeeId(rs.getInt("employeeId"));
                    employee.setEmployeeName(rs.getString("employeeName"));
                    employee.setTotalSales(rs.getBigDecimal("totalSales"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in real applications
        }

        return employees;
    }
    
}
