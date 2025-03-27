package config;

import java.sql.*;

public class MSSQLConnection extends DatabaseConnection {
    private static final String HOSTNAME = "localhost";
    private static final String INSTANCE_NAME = "JAVECILLA\\SQLEXPRESS01";
    private static final String PORT = "1433";
    private static final String DATABASE_NAME = "_2FG2";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "4545";

    private static final String URL = "jdbc:sqlserver://" + HOSTNAME 
            + ":" + PORT
            + ";instanceName=" + INSTANCE_NAME
            + ";databaseName=" + DATABASE_NAME
            + ";encrypt=true"
            + ";trustServerCertificate=true";

    private static Connection connection = null;

    private MSSQLConnection() {
        throw new UnsupportedOperationException("Cannot instantiate MSSQLConnection");
    }

    public static Connection getConnection() throws SQLException {
        try {
            if(connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
            return connection;
        } catch(SQLException | ClassNotFoundException e) {
            throw new SQLException("Failed to connect to MS SQL Server: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e) {
            System.err.println("Error closing MS SQL connection: " + e.getMessage());
        }
    }

    public static void closeResources(ResultSet rs, PreparedStatement pst) {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
        } catch (SQLException e) {
            System.err.println("Error closing MS SQL resources: " + e.getMessage());
        }
    }
}