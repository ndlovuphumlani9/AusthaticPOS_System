package com.mycompany.carol_boutique.ibt.utilities;

import com.mycompany.carol_boutique.ibt.model.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IBTUpdater {
    private static final Logger logger = Logger.getLogger(IBTUpdater.class.getName());
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Connection con; // Database connection

    public IBTUpdater(Connection con) {
        this.con = con;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::updateIBT, 0, 5, TimeUnit.MINUTES);
        logger.info("IBTUpdater started.");
    }

    public void stop() {
        scheduler.shutdown();
        logger.info("IBTUpdater stopped.");
    }

    private void updateIBT() {
        logger.info("IBTUpdater running at " + LocalDateTime.now());
        try (PreparedStatement ps = con.prepareStatement("select * from ibtrequests where TIMESTAMPDIFF(MINUTE, approvalTimestamp, NOW()) >= 5");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int ibtId = rs.getInt("ibtRequestId");
                Timestamp approvalTimestamp = rs.getTimestamp("approvalTimestamp");
                long minutesDifference = Duration.between(approvalTimestamp.toLocalDateTime(), LocalDateTime.now()).toMinutes();

                if (minutesDifference >= 3 && minutesDifference < 7) {
                    try (PreparedStatement psUpdate = con.prepareStatement("update ibtrequests set status = ?, deliveryTimestamp = CURRENT_TIMESTAMP where ibtRequestId = ?")) {
                        psUpdate.setString(1, Status.DELIVERED.toString());
                        psUpdate.setInt(2, ibtId);
                        psUpdate.executeUpdate();
                    }
                }
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}