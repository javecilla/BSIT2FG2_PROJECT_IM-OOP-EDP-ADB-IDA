package config;

import java.sql.*;
import interfaces.FIConnectionSupplier;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing MS Access Connection...");
        testConnection(
            MSACCESSConnection::getConnection,
            MSACCESSConnection::closeConnection
        );

        System.out.println("Testing MS SQL Server Connection...");
        testConnection(
            MSSQLConnection::getConnection,
            MSSQLConnection::closeConnection
        );
    }

    private static void testConnection(FIConnectionSupplier connectionSupplier, Runnable closeConnection) {
        Connection conn = null;
        try {
            conn = connectionSupplier.get();
            if(conn != null && !conn.isClosed()) {
                System.out.println("Successfully connected to the database!");
            }
        } catch(SQLException e) {
            System.err.println("Failed to connect: " + e.getMessage());
        } finally {
            closeConnection.run();
        }
    }
}