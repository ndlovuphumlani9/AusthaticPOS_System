/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.dao;

//import com.mycompany.carol_boutique.employ.model.Employee;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.IBT;
import com.mycompany.carol_boutique.ibt.model.Status;
import com.mycompany.carol_boutique.ibt.model.Stock;
import com.mycompany.carol_boutique.ibt.model.Store;
import static com.mycompany.carol_boutique.ibt.service.SMSService.sendSMS;
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
public class IBTDaoImpl implements IBTDao {

    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "MATHOthoana@20";
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public IBTDaoImpl() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Stock> companyAvailability(String code, int quantity) {
        List<Stock> stock = new ArrayList<>();
        try {
            ps = con.prepareStatement("select * from inventory where barcode = ? and quantity > ?");
            ps.setString(1, code);
            ps.setInt(2, quantity);
            rs = ps.executeQuery();

            while (rs.next()) {
                Stock item = new Stock();
                item.setQuantity(rs.getInt("quantity"));
                item.setBarcode(rs.getString("barcode"));
                item.setColour(rs.getString("color"));
                item.setSize(rs.getString("size"));
                int storeId = rs.getInt("storeId");

                PreparedStatement psStore = con.prepareStatement("select * from store where storeId = ?");
                psStore.setInt(1, storeId);
                ResultSet rsStore = psStore.executeQuery();

                if (rsStore.next()) {
                    item.setStoreId(rsStore.getInt("storeId"));
                    item.setStoreAddress(rsStore.getString("location"));
                }
                System.out.println(item);
                stock.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Stock item : stock) {
            System.out.println(item);
        }
        return stock;
    }

    @Override
    public boolean requestIBT(Stock stock, Employee employee, String customer) {
        try {
            ps = con.prepareStatement("update inventory set quantity = quantity - ? where barcode = ? and storeId = ?");
            ps.setInt(1, stock.getQuantity());
            ps.setString(2, stock.getBarcode());
            ps.setInt(3, stock.getStoreId());

            if (ps.executeUpdate() == 1) {
                PreparedStatement ps2 = con.prepareStatement("insert into ibtrequests(fromStoreId, toStoreId, employeeId, barcode, quantity, contactDetails, status) values(?,?,?,?,?,?,?)");
                ps2.setInt(1, stock.getStoreId());
                ps2.setInt(2, employee.getStoreId());
                ps2.setInt(3, employee.getEmployeeId());
                ps2.setString(4, stock.getBarcode());
                ps2.setInt(5, stock.getQuantity());
                ps2.setString(6, customer);
                ps2.setString(7, Status.REQUESTED.toString());

                return ps2.executeUpdate() == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<IBT> allIBTs(int employeeId) {
        List<IBT> ibts = new ArrayList<>();
        try {
            ps = con.prepareStatement("select * from ibtrequests where toStoreId = ?");
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                IBT ibt = new IBT();
                int storeFrom = rs.getInt("fromStoreId");
                ibt.setIbtId(rs.getInt("ibtRequestId"));
                ibt.setBarcode(rs.getString("barcode"));
                ibt.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                ibt.setQuantity(rs.getInt("quantity"));

                PreparedStatement ps2 = con.prepareStatement("select * from store where storeId = ?");
                ps2.setInt(1, storeFrom);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    ibt.setFrom(rs2.getString("storeName"));
                }
                ibts.add(ibt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ibts;
    }

    @Override
    public List<Store> allStores() {
        List<Store> stores = new ArrayList<>();
        try {
            ps = con.prepareStatement("select * from store");
            rs = ps.executeQuery();
            while (rs.next()) {
                Store store = new Store();
                store.setStoreId(rs.getInt("storeId"));
                int managerId = rs.getInt("managerId");
                store.setStoreName(rs.getString("storeName"));
                store.setLocation(rs.getString("location"));
                store.setEmail(rs.getString("storeEmail"));
                store.setNumber(rs.getString("storePhone"));

                PreparedStatement ps2 = con.prepareStatement("select * from employee where employeeId = ?");
                ps2.setInt(1, managerId);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    store.setManagerName(rs2.getString("firstName"));
                    store.setManagerSurname(rs2.getString("lastName"));
                }
                stores.add(store);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stores;
    }

    @Override
    public List<IBT> pending(int storeId) {
        List<IBT> ibts = new ArrayList<>();
        try {
            ps = con.prepareStatement("select * from ibtrequests");

            rs = ps.executeQuery();
            while (rs.next()) {
                IBT ibt = new IBT();
                int storeTo = rs.getInt("toStoreId");
                int storeFrom = rs.getInt("fromStoreId");
                ibt.setIbtId(rs.getInt("ibtRequestId"));
                ibt.setBarcode(rs.getString("barcode"));
                ibt.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                ibt.setQuantity(rs.getInt("quantity"));

                PreparedStatement ps2 = con.prepareStatement("select * from store where storeId = ?");
                ps2.setInt(1, storeTo);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    ibt.setTo(rs2.getString("location"));
                }

                PreparedStatement ps3 = con.prepareStatement("select * from store where storeId = ?");
                ps3.setInt(1, storeFrom);  // Corrected from storeTo to storeFrom
                ResultSet rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    ibt.setFrom(rs3.getString("location"));
                }

                ibts.add(ibt);
            }

        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ibts;
    }

    @Override
    public boolean approve(int ibtId) {
        try {
            ps = con.prepareStatement("update ibtrequests set status = ?, approvalTimestamp = CURRENT_TIMESTAMP where ibtRequestId = ?");
            ps.setString(1, Status.APPROVED.toString());
            ps.setInt(2, ibtId);

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String email(int storeId) {
        try {
            ps = con.prepareStatement("select * from store where storeId = ?");
            ps.setInt(1, storeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                return email;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean cancel(int id) {
        //delete from ibt table and add back into inventory table
        return true;
    }

    @Override
    public void updateIBT() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
     public boolean receive(int ibtId) {
        try {
            // Update the IBT request status
            ps = con.prepareStatement("UPDATE ibtrequests SET status = ?, receivedTimestamp = CURRENT_TIMESTAMP WHERE ibtRequestId = ?");
            ps.setString(1, Status.RECEIVED.toString());
            ps.setInt(2, ibtId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                PreparedStatement ps2 = con.prepareStatement("SELECT contactDetails FROM ibtrequests WHERE ibtRequestId = ?");
                ps2.setInt(1, ibtId);
                rs = ps2.executeQuery();

                if (rs.next()) {
                    System.out.println("sending sms");
                    sendSMS(rs.getString("contactDetails"), "Dear User,\n\nYou can collect your IBT.\n\nRegards,\nCarol's Boutique");
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IBTDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
