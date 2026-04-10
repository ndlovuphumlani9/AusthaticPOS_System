/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.product.dao;

import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employee.model.Role;
import static com.mycompany.carol_boutique.employee.service.EmployeeServiceImpl.doHashing;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.keepaside.model.Status;
import com.mycompany.carol_boutique.product.exception.IncorrectDetailsException;
import com.mycompany.carol_boutique.product.model.Product;
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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Mrqts
 */
public class ProductDaoImpl implements ProductDao {
    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    

    public ProductDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int addProduct(Product product) {
        try {
            ps = con.prepareStatement("insert into product(productName, category, price, description, product_type) values (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory().toString());
            ps.setLong(3, product.getPrice());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getProdType().toString());

            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int productId = generatedKeys.getInt(1);
                    System.out.println("product added");
                    return productId;
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            } else {
                throw new SQLException("Creating product failed, no rows affected.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public Item getItemByBarcode(String barcodeData) {
        try {
            ps = con.prepareStatement("select * from inventory where barcode = ?");
            ps.setString(1, barcodeData);

            rs = ps.executeQuery();

            if (rs.next()) {
                int prodId = rs.getInt("productId");
                Item item = new Item();
                item.setProductId(prodId);
                item.setSize(rs.getString("size"));
                item.setColour(rs.getString("color")); 
                item.setBarcode(barcodeData);

                PreparedStatement ps2 = con.prepareStatement("select * from product where productId = ?");
                ps2.setInt(1, prodId);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    item.setPrice(rs2.getLong("price"));
                    item.setDescription(rs2.getString("description"));
                    item.setName(rs2.getString("productName"));
                }
                return item;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
    public int process(List<Item> items, Employee employee, double total, String email, Long cardNo) {

        try {
            PreparedStatement psSelectInventory = con.prepareStatement("SELECT * FROM inventory WHERE barcode = ? and storeId = ?");
            PreparedStatement psSelectIbtRequests = con.prepareStatement("SELECT * FROM ibtrequests WHERE barcode = ? AND status = 'received'");
            PreparedStatement psInsertSale = con.prepareStatement("INSERT INTO sales(storeId, employeeId, customerEmail, totalAmount, numberOfItems, cardNumber) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psInsertSaleItems = con.prepareStatement("INSERT INTO saleitems(storeId, employeeId, saleId, barcode, price) VALUES (?, ?, ?, ?, ?)");
            PreparedStatement psUpdateInventory = con.prepareStatement("UPDATE inventory SET quantity = quantity - ? WHERE barcode = ? AND storeId = ?");
            PreparedStatement psUpdateSaleTotal = con.prepareStatement("UPDATE sales SET totalAmount = ? WHERE saleId = ?");
            PreparedStatement psUpdateIbtRequests = con.prepareStatement("UPDATE ibtrequests SET status = 'collected' WHERE barcode = ? AND status = 'received'");

            psInsertSale.setInt(1, employee.getStoreId());
            psInsertSale.setInt(2, employee.getEmployeeId());
            psInsertSale.setString(3, email);
            psInsertSale.setDouble(4, total);
            psInsertSale.setInt(5, items.size() - 1);
            psInsertSale.setLong(6, cardNo);
            psInsertSale.executeUpdate();

            rs = psInsertSale.getGeneratedKeys();
            int saleId = -1;
            if (rs.next()) {
                saleId = rs.getInt(saleId);
            } else {
                throw new SQLException("Failed to retrieve generated sale ID");
            }

            double adjustedTotal = total;
            List<String> processedBarcodes = new ArrayList<>();

            for (Item item : items) {
                psSelectInventory.setString(1, item.getBarcode());
                psSelectInventory.setInt(2, employee.getStoreId());
                ResultSet rsInventory = psSelectInventory.executeQuery();

                boolean itemInInventory = rsInventory.next();
                int storeId = employee.getStoreId();
                double itemAmount = item.getPrice();

                if (itemInInventory) {
                    int quantity = rsInventory.getInt("quantity");
                    if (quantity == 0) {
                        psSelectIbtRequests.setString(1, item.getBarcode());
                        ResultSet rsIbtRequests = psSelectIbtRequests.executeQuery();

                        if (rsIbtRequests.next()) {
                            storeId = rsIbtRequests.getInt("fromStoreId");
                            adjustedTotal -= item.getPrice();
                            processedBarcodes.add(item.getBarcode());
                        }

                        psInsertSaleItems.setInt(1, storeId);
                        psInsertSaleItems.setInt(2, 0);
                        psInsertSaleItems.setInt(3, saleId);
                        psInsertSaleItems.setString(4, item.getBarcode());
                        psInsertSaleItems.setDouble(5, itemAmount);
                        psInsertSaleItems.executeUpdate();

                    } else {
                        psInsertSaleItems.setInt(1, employee.getStoreId());
                        psInsertSaleItems.setInt(2, employee.getEmployeeId());
                        psInsertSaleItems.setInt(3, saleId);
                        psInsertSaleItems.setString(4, item.getBarcode());
                        psInsertSaleItems.setDouble(5, item.getPrice());
                        psInsertSaleItems.executeUpdate();

                        psUpdateInventory.setInt(1, 1);
                        psUpdateInventory.setString(2, item.getBarcode());
                        psUpdateInventory.setInt(3, employee.getStoreId());
                        psUpdateInventory.executeUpdate();
                    }
                } else {
                    psSelectIbtRequests.setString(1, item.getBarcode());
                    ResultSet rsIbtRequests = psSelectIbtRequests.executeQuery();

                    if (rsIbtRequests.next()) {
                        storeId = rsIbtRequests.getInt("fromStoreId");
                        adjustedTotal -= item.getPrice();
                        processedBarcodes.add(item.getBarcode());

                        psInsertSaleItems.setInt(1, storeId);
                        psInsertSaleItems.setInt(2, 0);
                        psInsertSaleItems.setInt(3, saleId);
                        psInsertSaleItems.setString(4, item.getBarcode());
                        psInsertSaleItems.setDouble(5, itemAmount);
                        psInsertSaleItems.executeUpdate();
                    } else {
                        System.out.println("Item with barcode " + item.getBarcode() + " not found in inventory or IBT requests.");
                    }
                }
            }

            psUpdateSaleTotal.setDouble(1, adjustedTotal);
            psUpdateSaleTotal.setInt(2, saleId);
            psUpdateSaleTotal.executeUpdate();

            for (String barcode : processedBarcodes) {
                psUpdateIbtRequests.setString(1, barcode);
                psUpdateIbtRequests.executeUpdate();
            }

            System.out.println("Processed successfully");
            return saleId;

        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    
    @Override
    public Employee manager(String username, String password) {
        try {
            ps = con.prepareStatement("Select * from employee where employeeId=? and role = 'manager'");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String dbPassword=rs.getString("passwordHash");
               String pass = doHashing(password);
                if (pass.equals(dbPassword)) {
                    System.out.println("Manager found!");
                    
                    return new Employee(rs.getInt("employeeId"), rs.getString("firstName"), rs.getString("lastName"), Role.valueOf(rs.getString("role").toUpperCase()), rs.getString("email"), rs.getInt("storeId"), rs.getString("passwordHash"), rs.getString("phone"));
                }else{
              throw new IncorrectDetailsException("Invalid email or password");

                }
            } else {
                System.out.println("User not found!");
                throw new IncorrectDetailsException("Invalid email or password");
            }

        } catch (SQLException | IncorrectDetailsException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
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
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getStoreId(int ibtId3) {
        try {
            ps = con.prepareStatement("select * from ibtrequests where ibtRequestId = ?");
            ps.setInt(1, ibtId3);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("fromStoreId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
