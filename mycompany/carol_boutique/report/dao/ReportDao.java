package com.mycompany.carol_boutique.report.dao;

import com.mycompany.carol_boutique.report.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportDao {
    public ResultSet getTopSellingProducts(Date startDate, Date endDate) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT product.productName, SUM(saleitems.quantity) as totalQuantity " +
                     "FROM saleitems " +
                     "JOIN product ON saleitems.productId = product.productId " +
                     "WHERE saleitems.saleTimestamp BETWEEN ? AND ? " +
                     "GROUP BY product.productId " +
                     "ORDER BY totalQuantity DESC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, startDate);
        stmt.setDate(2, endDate);
        return stmt.executeQuery();
    }
}
