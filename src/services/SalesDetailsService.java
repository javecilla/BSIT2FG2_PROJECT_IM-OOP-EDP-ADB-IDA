package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Sale;
import models.Food;
import models.SalesDetails;
import config.DBConnection;
import interfaces.IDatabaseOperators;

public class SalesDetailsService implements IDatabaseOperators<SalesDetails>{

    @Override
    public boolean create(SalesDetails salesDetails) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
              
            String query = """
                INSERT INTO SALES_DETAIL (Food_ID, Sales_ID, Item_Quantity) VALUES (?, ?, ?)
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, salesDetails.getFood().getFoodId());
            pst.setInt(2, salesDetails.getSale().getSaleId());
            pst.setInt(3, salesDetails.getItemQuantity());
            
            success = pst.executeUpdate() > 0;
            
            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
            
            return success;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }

    @Override
    public SalesDetails getById(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SalesDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(SalesDetails entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
