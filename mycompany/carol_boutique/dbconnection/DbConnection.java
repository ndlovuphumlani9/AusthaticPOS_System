/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/carol_db?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    //private static final String JDBC_DRIVER ="com.mysql.cj.jdbc.Driver";
    
    public static Connection getConnection()
    {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to DB: " + e.getMessage());
        }
        return connection;
    }
    
}

// DBPoolManger