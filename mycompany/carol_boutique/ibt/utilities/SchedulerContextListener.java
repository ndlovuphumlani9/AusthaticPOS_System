package com.mycompany.carol_boutique.ibt.utilities;

import com.mycompany.carol_boutique.keepaside.utilities.KeepAsideExpire;
import com.mycompany.carol_boutique.keepaside.utilities.KeepAsideUpdater;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class SchedulerContextListener implements ServletContextListener {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carol_database?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "MATHOthoana@20";
    
    private IBTUpdater updater;
    private KeepAsideUpdater updater2;
    private KeepAsideExpire updater3;
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(SchedulerContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Load the JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Establish the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Initialize and start the updaters
            updater = new IBTUpdater(connection);
            updater.start();
                       
            updater2 = new KeepAsideUpdater(connection);
            updater2.start();
            
            updater3 = new KeepAsideExpire(connection);
            updater3.start();
            
            // Store updaters in servlet context for potential future use
            sce.getServletContext().setAttribute("updater", updater);
            sce.getServletContext().setAttribute("updater2", updater2);
            sce.getServletContext().setAttribute("updater3", updater3);

            LOGGER.info("SchedulerContextListener initialized and updaters started.");

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "JDBC Driver not found", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (updater != null) {
            updater.stop();
            LOGGER.info("IBTUpdater stopped.");
        }
        
        if (updater2 != null) {
            updater2.stop();
            LOGGER.info("KeepAsideUpdater stopped.");
        }
        
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Database connection closed.");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }
}
