package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public final class DBConnection {
    private static final String MS_ACCESS_PATH = "C://Users//Server//Documents//NetBeansProjects//PROJECT_ECOMMERCE//src//config//Mommys-Variety-Store-Database.accdb";
    private static final String URL = "jdbc:ucanaccess://" + MS_ACCESS_PATH;
    private static Connection connection = null;
    
    //Private constructor to prevent instantiation
    private DBConnection() {}
    
    //Get database connection
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
            return connection;
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage());
        }
    }
    
    //Close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    //Close ResultSet, PreparedStatement, and Connection
    public static void closeResources(ResultSet rs, PreparedStatement pst) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}