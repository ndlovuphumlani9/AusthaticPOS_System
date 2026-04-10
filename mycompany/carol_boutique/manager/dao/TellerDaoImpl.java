package com.mycompany.carol_boutique.manager.dao;

import com.mycompany.carol_boutique.dbconnection.DbConnection;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employee.model.Role;
//import com.mycompany.carol_boutique.manager.model.Teller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TellerDaoImpl implements TellerDao {

    
    private DbConnection dConnection;
//    @Override
//    public boolean addTeller(Employee teller) {
//        String query = "INSERT INTO employee (firstName, lastName,role, email, phone, storeId, passwordHash) VALUES (?, ?, ?, ?, ?, ?,?)";
//        try (Connection conn = dConnection.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(query))
//        {
//            stmt.setString(1, teller.getFirstName());
//            stmt.setString(2, teller.getLastName());
//            stmt.setString(3, "teller");
//            stmt.setString(4, teller.getEmail());
//            stmt.setString(5, teller.getPhone());
//            stmt.setInt(6, teller.getStoreId());
//            stmt.setString(7, teller.getPassword());
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    @Override
//    public Employee getTellerByUsername(int username) {
//        String query = "SELECT * FROM employee WHERE employeeId = ?";
//        try(Connection conn = dConnection.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, username);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Employee teller = new Employee();
//                teller.getEmployeeId(rs.getInt("employeeId"));
//                teller.setFirstName(rs.getString("firstName"));
//                teller.setLastName(rs.getString("lastName"));
//                teller.setPhone(rs.getString("phone"));
//                teller.setPassword(rs.getString("passwordHash"));
//                teller.setRole(rs.getString("role"));
//                teller.setEmail(rs.getString("email"));
//                return teller;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

     @Override
    public List<Employee> getAllTellers() 
    {
        List<Employee> tellers = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE role = 'teller'";
        try (Connection conn = dConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee teller = new Employee();
                teller.setEmployeeId(rs.getInt("employeeId"));
                teller.setFirstName(rs.getString("firstName"));
                teller.setLastName(rs.getString("lastName"));
                teller.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                teller.setEmail(rs.getString("email"));
                teller.setPhone(rs.getString("phone"));
                teller.setStoreId(rs.getInt("storeId"));
                tellers.add(teller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tellers;
    }
}
