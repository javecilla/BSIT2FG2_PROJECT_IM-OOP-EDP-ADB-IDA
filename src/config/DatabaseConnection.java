package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseConnection {
    //Get database connection
    public static Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Must be implemented by subclass");
    }

    //Close database connection
    public static void closeConnection() {
        throw new UnsupportedOperationException("Must be implemented by subclass");
    }

    //Close ResultSet, PreparedStatement, and Connection
    public static void closeResources(ResultSet rs, PreparedStatement pst) {
        throw new UnsupportedOperationException("Must be implemented by subclass");
    }
}