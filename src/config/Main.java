/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
/**
 *
 * @author Admin
 */
public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = MSSQLConnection.getConnection();
            System.out.println("✅ Successfully connected to your local database!");
            MSSQLConnection.closeConnection();
        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
        }
    }
}
