/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.sale.dao;

import com.mycompany.carol_boutique.ibt.dao.IBTDaoImpl;
import com.mycompany.carol_boutique.ibt.model.Item;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mrqts
 */
public class SaleDaoImpl implements SaleDao {
    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public SaleDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaleDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(SaleDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean processSale(List<Item> items) {
        try {
            for (Item item : items) {
                ps = con.prepareStatement("insertinto sales(");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
