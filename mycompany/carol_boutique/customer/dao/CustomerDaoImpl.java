/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.customer.dao;

import com.mycompany.carol_boutique.customer.exception.EmailExistsException;
import com.mycompany.carol_boutique.customer.model.Method;
import com.mycompany.carol_boutique.customer.model.Review;
import com.mycompany.carol_boutique.ibt.model.Store;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mrqts
 */
public class CustomerDaoImpl implements CustomerDao {
    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public CustomerDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean addReview(Review review) {
        try {
            System.out.println("outchea");
            ps = con.prepareStatement("insert into rating(storeId, customerName, number, rating, comment) values(?,?,?,?,?)");
            ps.setInt(1, review.getStoreId());
            ps.setString(2, review.getName());
            ps.setString(3, review.getNumber());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getComment());
            System.out.println("inserted");
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public String subscribe(String name, String number, String email, Method method) throws EmailExistsException {
        try {
            ps = con.prepareStatement("select * from newslettersubscription where email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (!rs.next()) {
                PreparedStatement psAdd = con.prepareStatement("insert into newslettersubscription(name, number, email, method) values(?,?,?,?)");
                psAdd.setString(1, name);
                psAdd.setString(2, number);
                psAdd.setString(3, email);
                psAdd.setString(4, method.toString());
                if (psAdd.executeUpdate() == 1) {
                    return "Successfully subscribed to Newsletter.";
                }
            } else {
                throw new EmailExistsException("Email already exists");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Not subscribed to Newsletter.";
    }
}
