/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.utilities;


import static com.mycompany.carol_boutique.keepaside.email.SendEmail.sendEmail;
import com.mycompany.carol_boutique.keepaside.model.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeepAsideUpdater {
    private static final Logger logger = Logger.getLogger(KeepAsideUpdater.class.getName());
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Connection con;

    public KeepAsideUpdater(Connection con) {
        this.con = con;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::KeepAsideUpdater, 0, 1, TimeUnit.HOURS);
        logger.info("KeepAsideUpdater started.");
    }

    public void stop() {
        scheduler.shutdown();
        logger.info("KeepAsideUpdater stopped.");
    }

    private void KeepAsideUpdater() {
        logger.info("KeepAsideUpdater running at " + LocalDateTime.now());

        try (PreparedStatement ps = con.prepareStatement("select keepAsideId, reserveTimestamp, email from keepaside where status = ? and TIMESTAMPDIFF(HOUR, reserveTimestamp, NOW()) BETWEEN 35 AND 37")) {
            ps.setString(1, Status.RESERVED.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int keepAsideId = rs.getInt("keepAsideId");
                    String email = rs.getString("email");

                    try {
                        sendEmail(email, "You have less than 12 hours to collect your item(s).");
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Failed to send email to: " + email, e);
                    }

                    try (PreparedStatement psUpdate = con.prepareStatement("update keepaside set status = ? where keepAsideId = ?")) {
                        psUpdate.setString(1, Status.NOTIFIED.toString());
                        psUpdate.setInt(2, keepAsideId);
                        psUpdate.executeUpdate();
                    } catch (SQLException e) {
                        logger.log(Level.SEVERE, "Failed to update status for keepAsideId: " + keepAsideId, e);
                    }
                }
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database error occurred", ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected error occurred", ex);
        }
    }


}

