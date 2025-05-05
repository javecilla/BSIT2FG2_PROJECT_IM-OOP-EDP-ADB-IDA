package services;

import config.MSSQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.IDatabaseOperators;
import java.util.Arrays;
import java.util.List;
import models.Supplier;


public class SupplierService implements IDatabaseOperators<Supplier> {
    public boolean isSupplierExistsById(int supplierId) throws SQLException {
        Connection conn = null;
        String query = """
            SELECT COUNT(*) FROM SUPPLIER WHERE Supplier_ID = ?
        """;
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean create(Supplier entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Supplier getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            
            String query = """
                SELECT TOP 1 * FROM SUPPLIER WHERE Supplier_ID = ? 
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("Supplier_ID"));
                supplier.setSupplierName(rs.getString("Supplier_Name"));
                supplier.setContactNumber(rs.getString("Supplier_Contact"));
                supplier.setAddress(rs.getString("Supplier_Address"));
                
                return supplier;
            }
            
            return null;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved supplier an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        }
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
