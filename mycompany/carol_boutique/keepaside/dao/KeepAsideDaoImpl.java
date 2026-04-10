/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.dao;

import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import static com.mycompany.carol_boutique.keepaside.email.SendEmail.sendEmail;
import com.mycompany.carol_boutique.keepaside.exception.EmailNotSentException;
import com.mycompany.carol_boutique.keepaside.model.KeepAside;
import com.mycompany.carol_boutique.keepaside.model.Status;
import com.mycompany.carol_boutique.product.dao.ProductDaoImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mrqts
 */
public class KeepAsideDaoImpl implements KeepAsideDao {
    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public KeepAsideDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KeepAsideDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(KeepAsideDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean addKeepAside(String barcode, String email, Employee employee) {
        try {
            ps = con.prepareStatement("insert into keepaside(barcode, email, employeeId, status, storeId) values (?,?,?,?,?)");
            ps.setString(1, barcode);
            ps.setString(2, email);
            ps.setInt(3, employee.getEmployeeId());
            ps.setString(4, Status.RESERVED.toString());
            ps.setInt(5, employee.getStoreId());

            if (ps.executeUpdate() == 1) {               
                PreparedStatement psUpdate = con.prepareStatement("update inventory set quantity = quantity - 1 where barcode = ? and storeId = ?");
                psUpdate.setString(1, barcode);
                psUpdate.setInt(2, employee.getStoreId());
                return psUpdate.executeUpdate() == 1;
            }

            con.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void updateKeepAside() {
        
    }

    @Override
    public String getProduct(String productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<KeepAside> allKeepAside(int storeId) {
        List<KeepAside> list = new ArrayList<>();
        try {
            ps = con.prepareStatement("select * from keepaside where storeId = ?");
            ps.setInt(1, storeId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                KeepAside item = new KeepAside();
                item.setBarcode(rs.getString("barcode"));
                item.setStoreId(rs.getInt("storeId"));
                item.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                item.setEmployeeId(rs.getInt("employeeId"));
                item.setEmail(rs.getString("email"));
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KeepAsideDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

  
}
