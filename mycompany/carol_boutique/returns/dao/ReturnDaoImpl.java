/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.returns.dao;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
//import com.mycompany.carol_boutique.product.dao.ProductDaoImpl;
import com.mycompany.carol_boutique.returns.model.Reason;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mrqts
 */
public class ReturnDaoImpl implements ReturnDao {
    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public ReturnDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Item> returns(int saleId) {
        List<Item> items = new ArrayList<>();
        try {
            ps = con.prepareStatement(
                "SELECT DISTINCT i.productId, p.price, i.size, i.color, p.description, p.productName AS name,\n" +
"       si.barcode, si.saleItemId\n" +
"FROM saleitems si\n" +
"INNER JOIN inventory i ON si.barcode = i.barcode\n" +
"INNER JOIN product p ON i.productId = p.productId\n" +
"WHERE si.saleId = ?");
            ps.setInt(1, saleId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                double price = rs.getDouble("price");
                String size = rs.getString("size");
                String colour = rs.getString("color");
                String description = rs.getString("description");
                String name = rs.getString("name");
                String barcode = rs.getString("barcode");
                int saleItemId = rs.getInt("saleItemId");

                Item item = new Item(productId, price, size, colour, description, name, barcode, saleItemId);
                items.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
        return items;
    }







    @Override
    public String[] processReturn(int itemId, Reason reason, Employee employee) {
        String[] details= new String[2];
        try {
            ps = con.prepareStatement("select * from saleItems where saleItemId = ?");
            ps.setInt(1, itemId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int saleId = rs.getInt("saleId");
                PreparedStatement ps2 = con.prepareStatement("insert into returns(storeId, employeeId, saleId, saleItemId, reason) values (?,?,?,?,?)");
                ps2.setInt(1, employee.getStoreId());
                ps2.setInt(2, employee.getEmployeeId());
                ps2.setInt(3, saleId);
                ps2.setInt(4, itemId);
                ps2.setString(5, reason.toString());
                
                if (ps2.executeUpdate() == 1) {
                    PreparedStatement ps3 = con.prepareStatement("select * from sales where saleId = ?");
                    ps3.setInt(1, saleId);
                    
                    ResultSet rs2 = ps3.executeQuery();
                    
                    if (rs2.next()) {
                        details[0] = rs2.getString("cardNumber");
                        details[1] = rs2.getString("customerEmail");
                        System.out.println(details[0]);
                        System.out.println(details[1]);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }

    @Override
    public int processExchange(int saleItemId, String barcode) {
        int saleId = -1; // Default value if saleId is not found or not updated

        try {
            String[] barcodeParts = barcode.split("-");
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM product WHERE productId = ?");
            ps2.setInt(1, Integer.parseInt(barcodeParts[0]));
            rs = ps2.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                ps = con.prepareStatement("UPDATE saleItems SET barcode = ?, price = ? WHERE saleItemId = ?");
                ps.setString(1, barcode);
                ps.setDouble(2, price);
                ps.setInt(3, saleItemId);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    PreparedStatement ps3 = con.prepareStatement("SELECT saleId FROM saleItems WHERE saleItemId = ?");
                    ps3.setInt(1, saleItemId);
                    ResultSet rs2 = ps3.executeQuery();

                    if (rs2.next()) {
                        saleId = rs2.getInt("saleId");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(saleId);
        return saleId;
    }

    
    public List<Item> getReceipt(int saleId) {
        List<Item> items = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM saleItems WHERE saleId = ?");
            ps.setInt(1, saleId);
            rs = ps.executeQuery();

            while (rs.next()) {
                String barcode = rs.getString("barcode");
                String[] barcodeParts = barcode.split("-");
                int productId = Integer.parseInt(barcodeParts[0]);

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM product WHERE productId = ?");
                ps2.setInt(1, productId);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    Item item = new Item();
                    item.setName(rs2.getString("productName"));
                    item.setPrice(rs2.getDouble("price"));
                    item.setDescription(rs2.getString("description"));
                    item.setBarcode(barcode);
                    items.add(item);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    

    @Override
    public String storeAddress(int storeId) {
        try {
            ps = con.prepareStatement("select * from store where storeId = ?");
            ps.setInt(1, storeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("location");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
