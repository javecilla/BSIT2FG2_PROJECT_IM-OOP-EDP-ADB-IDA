package config;

import java.sql.*;

public final class MSACCESSConnection extends DatabaseConnection {
    private static final String DB_SOURCE_PATH = "C://Users//HomePC//OneDrive//NetBeansProjects//BSIT2FG2_PROJECT_IM-OOP-EDP-ADB-IDA//src//config//Mommys-Variety-Store-Database-new-revision.accdb";
    private static final String MS_ACCESS_PATH = DB_SOURCE_PATH;
    private static final String URL = "jdbc:ucanaccess://" + MS_ACCESS_PATH;
    
    private static Connection connection = null;

    private MSACCESSConnection() {
        throw new UnsupportedOperationException("Cannot instantiate MSACCESSConnection");
    }

    public static Connection getConnection() throws SQLException {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
            return connection;
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to MS Access database: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e) {
            System.err.println("Error closing MS Access connection: " + e.getMessage());
        }
    }

    public static void closeResources(ResultSet rs, PreparedStatement pst) {
        try {
            if(rs != null) rs.close();
            if(pst != null) pst.close();
        } catch(SQLException e) {
            System.err.println("Error closing MS Access resources: " + e.getMessage());
        }
    }
}