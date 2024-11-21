package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Sale;
import models.Food;
import models.Ingredient;
import config.DBConnection;
import interfaces.IDatabaseOperators;
import helpers.Date;

public class SaleService implements IDatabaseOperators<Sale> {

    @Override
    public boolean create(Sale sale) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet generatedKeys = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String query = """
                INSERT INTO SALE (Sales_Date, Customer_ID, Net_Total) VALUES (?, ?, ?)
            """;

            //retrieve the auto-generated Sale_ID upon creating
            pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, Date.formatDateForSQL(sale.getSaleDate()));
            pst.setInt(2, sale.getCustomerId());
            pst.setDouble(3, sale.getNetTotal());

            success = pst.executeUpdate() > 0;

            // Retrieve and set the newly generated Sale_ID
            if(success) {
                generatedKeys = pst.getGeneratedKeys();
                if(generatedKeys.next()) {
                    int saleId = generatedKeys.getInt(1); 
                    sale.setSaleId(saleId); 
                }
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
            DBConnection.closeResources(generatedKeys, pst);
            if (conn != null) conn.close();
        }
    }


    @Override
    public Sale getById(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Sale> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Sale entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
