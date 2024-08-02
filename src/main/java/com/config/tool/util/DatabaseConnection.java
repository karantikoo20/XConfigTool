package com.config.tool.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
public class DatabaseConnection {

    private Connection connection;

    /**
     * Initializes the database connection using dynamic configuration.
     *
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver class is not found.
     */
    public DatabaseConnection() throws SQLException, ClassNotFoundException {
        // Load the Oracle JDBC driver
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // Get the database connection details from the dynamic configuration
        String url = DatabaseConfig.getDatabaseUrl();
        String username = DatabaseConfig.getUsername();
        String password = DatabaseConfig.getPassword();

        // Establish the connection using the dynamically obtained credentials
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Gets the current database connection.
     *
     * @return the current database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the current database connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
