/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.employee.dao;

import com.mycompany.carol_boutique.dbconnection.DbConnection;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employee.model.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private DbConnection dConnection;

    @Override
    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employee (firstName, lastName, passwordHash, role, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getPassword());
            stmt.setString(4, employee.getRole().toString());
            stmt.setString(5, employee.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addManager(Employee employee) {
        String query = "INSERT INTO employee (firstName, lastName,role, email, phone, storeId, passwordHash) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getRole().toString());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhone());
            stmt.setInt(6, employee.getStoreId());
            stmt.setString(7, employee.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Employee getEmployeeByUsername(int username) {
        String query = "SELECT * FROM employee WHERE employeeId = ?";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setPassword(rs.getString("passwordHash"));
                employee.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                employee.setEmail(rs.getString("email"));
                employee.setStoreId(rs.getInt("storeId"));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        String query = "SELECT * FROM employee WHERE email = ?";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setPassword(rs.getString("passwordHash"));
                employee.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                employee.setEmail(rs.getString("email"));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePassword(int employeeId, String newPasswordHash) {
        String query = "UPDATE employee SET passwordHash = ? WHERE employeeId = ?";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPasswordHash);
            stmt.setInt(2, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Employee> getAllManagers() {
        List<Employee> managers = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE role = 'manager'";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee manager = new Employee();
                manager.setEmployeeId(rs.getInt("employeeId"));
                manager.setFirstName(rs.getString("firstName"));
                manager.setLastName(rs.getString("lastName"));
                manager.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                manager.setEmail(rs.getString("email"));
                manager.setPhone(rs.getString("phone"));
                manager.setStoreId(rs.getInt("storeId"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }

    @Override
    public boolean deleteManager(int employeeId) {
        String query = "DELETE FROM employee WHERE employeeId = ?";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean doesManagerExist(int managerId) {
        String query = "SELECT COUNT(*) FROM employee WHERE employeeId = ? AND role = 'manager'";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   
    @Override
    public boolean deleteTeller(int employeeId) {
        String query = "DELETE FROM employee WHERE employeeId = ? AND WHERE role = 'teller'";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employee> getAllTellers() {
        List<Employee> tellers = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE role = 'teller'";
        try (Connection conn = dConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
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
   
    @Override
    public boolean addTeller(Employee employee) {
    String query = "INSERT INTO employee (firstName, lastName, role, email, phone, storeId, passwordHash) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = dConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, employee.getFirstName());
        stmt.setString(2, employee.getLastName());
        stmt.setString(3, employee.getRole().toString());
        stmt.setString(4, employee.getEmail());
        stmt.setString(5, employee.getPhone());
        stmt.setInt(6, employee.getStoreId());
        stmt.setString(7, employee.getPassword());
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
