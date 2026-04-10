package com.mycompany.carol_boutique.verification.dao;

import com.mycompany.carol_boutique.verification.model.Otp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OtpDaoImpl implements OtpDao {

    private final String URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private final String USER_NAME = "root";
    private final String PASSWORD = "MATHOthoana@20";
    private Connection conn;

    public OtpDaoImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Could not make a connection in the OTP dao" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public boolean saveOtp(Otp otp) {
        String query = "INSERT INTO otp (email, otpCode, expiryTime) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE otpCode = VALUES(otpCode), expiryTime = VALUES(expiryTime)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, otp.getEmail());
            stmt.setString(2, otp.getOtpCode());
            stmt.setTimestamp(3, otp.getExpiryTime());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Otp getOtpByEmail(String email) {
        String query = "SELECT * FROM otp WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Otp(
                        rs.getString("email"),
                        rs.getString("otpCode"),
                        rs.getTimestamp("expiryTime")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteOtpByEmail(String email) {
        String query = "DELETE FROM otp WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
