/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.store.dao;

/**
 *
 * @author Poloko
 */
import com.mycompany.carol_boutique.employee.dao.EmployeeDao;
import com.mycompany.carol_boutique.employee.dao.EmployeeDaoImpl;
import com.mycompany.carol_boutique.store.model.Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDaoImpl implements StoreDao {

    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER_NAME = "root";
    private final String PASSWORD = "MATHOthoana@20";
    private Connection conn;
    EmployeeDao employeeDao = new EmployeeDaoImpl();

    public StoreDaoImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Could not make a connection in the store dao: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public boolean addStore(Store store) {
//        if (!employeeDao.doesManagerExist(store.getManagerId())) {
//            System.out.println("Manager does not exist.");
//            return false;
//        }

        String query = "INSERT INTO store (storeName, location, storePhone, storeEmail) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "Carol's Boutique");
            stmt.setString(2, store.getLocation());
            stmt.setString(3, store.getStorePhone());
            stmt.setString(4, store.getStoreEmail());
            System.out.println("Store added");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateStore(Store store) {
        String query = "UPDATE store SET location = ?, storePhne = ? ,storeEmail = ? WHERE storeId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, store.getLocation());
            stmt.setString(2, store.getStorePhone());
            stmt.setString(3, store.getStoreEmail());
            stmt.setInt(4, store.getStoreId());
            System.out.println("Store updated");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStore(int storeId) {
        String query = "DELETE FROM store WHERE storeId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, storeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Store> getAllStores() {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT * FROM store";
        try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Store store = new Store(
                        rs.getInt("storeId"),
                        rs.getString("storeName"),
                        rs.getString("location"),
                        rs.getString("storePhone"),
                        rs.getString("storeEmail")
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stores;
    }

    // Close the connection
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Store getStoreById(int storeId) {
        
        String query = "SELECT * FROM store WHERE storeId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Store store = new Store();
                store.setStoreId(rs.getInt("storeId"));
                store.setStoreName(rs.getString("storeName"));
                store.setLocation(rs.getString("location"));
                store.setStorePhone(rs.getString("storePhone"));
                store.setStoreEmail(rs.getString("storeEmail"));
                return store;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getTotalStores() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM store"; // Adjust the table name as necessary

        try (
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return count;
    }
}
