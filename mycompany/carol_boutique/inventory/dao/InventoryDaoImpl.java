/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.inventory.dao;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.inventory.model.Inventory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mrqts
 */
public class InventoryDaoImpl implements InventoryDao {

    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public InventoryDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InventoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean addToInventory(List<Item> items, Employee employee) {
        boolean allSuccess = true;

        try {
            for (Item item : items) {
                System.out.println("checking inventory");
                ps = con.prepareStatement("select quantity from inventory where storeId = ? and barcode = ?");
                ps.setInt(1, employee.getStoreId());
                ps.setString(2, item.getBarcode());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("item in inventory");
                    int currentQuantity = rs.getInt("quantity");
                    ps = con.prepareStatement("update inventory set quantity = ? where storeId = ? and barcode = ?");
                    ps.setInt(1, currentQuantity + 1);
                    ps.setInt(2, employee.getStoreId());
                    ps.setString(3, item.getBarcode());
                    if (ps.executeUpdate() != 1) {
                        allSuccess = false;
                    }
                } else {
                    System.out.println("item not in inventory");
                    ps = con.prepareStatement("insert into inventory(storeId, productId, barcode, size, color, quantity) values (?, ?, ?, ?, ?, 1)");
                    String[] barcodeParts = item.getBarcode().split("-");
                    ps.setInt(1, employee.getStoreId());
                    ps.setInt(2, Integer.parseInt(barcodeParts[0]));
                    ps.setString(3, item.getBarcode());
                    ps.setString(4, barcodeParts[1]);
                    ps.setString(5, barcodeParts[2]);
                    if (ps.executeUpdate() != 1) {
                        allSuccess = false;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            allSuccess = false;
        }

        return allSuccess;
    }

    @Override
    public List<Stock> lowStock(Employee employee) {
        List<Stock> stock = new ArrayList<>();
        try {
            ps = con.prepareStatement("select * from inventory where storeId = ? and quantity < 5");
            ps.setInt(1, employee.getStoreId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Stock item = new Stock();
                item.setColour(rs.getString("color"));
                item.setBarcode(rs.getString("barcode"));
                item.setSize(rs.getString("size"));
                item.setQuantity(rs.getInt("quantity"));

                stock.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stock;
    }

    @Override
    public Item getItemByProductId(int productId) {
        try {
            System.out.println("here");
            ps = con.prepareStatement("select * from product where productId = ?");
            ps.setInt(1, productId);

            rs = ps.executeQuery();

            if (rs.next()) {
                Item item = new Item();
                item.setProductId(productId);
                item.setDescription(rs.getString("description"));
                item.setName(rs.getString("productName"));
                item.setPrice(rs.getLong("price"));
                System.out.println("item");
                return item;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    

}
