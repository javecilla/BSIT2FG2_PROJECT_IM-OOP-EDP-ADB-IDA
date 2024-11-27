package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Sale;
import config.DBConnection;
import enums.CourierStatus;
import interfaces.IDatabaseOperators;
import helpers.Date;
import helpers.Response;
import helpers.Text;
import java.util.Collections;
import java.util.Random;
import models.Courier;

public class SaleService implements IDatabaseOperators<Sale> {
    
    protected int getLastSaleId() throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String query = "SELECT MAX(Sales_ID) AS last_sales_id FROM SALE";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            return rs.next() ? rs.getInt("last_sales_id") : 0;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving last sale ID: " + e.getMessage(), e);
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }

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
                INSERT INTO SALE (Sales_Date, Customer_ID, Net_Total, Payment_Amount, Rider_ID) VALUES (?, ?, ?, ?, ?)
            """;

            //retrieve the auto-generated Sale_ID upon creating
            pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, Date.formatDateForSQL(sale.getSaleDate()));
            pst.setInt(2, sale.getCustomer().getCustomerId());
            pst.setDouble(3, sale.getNetTotal());
            pst.setDouble(4, sale.getPaymentAmount());
            pst.setInt(5, sale.getCourier().getRiderId());
            //pst.setInt(5, selectedRiderId);

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
            //if (conn != null) conn.close();
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
