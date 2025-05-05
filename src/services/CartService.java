package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.MSSQLConnection;
import models.Cart;
import interfaces.IDatabaseOperators;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CartService implements IDatabaseOperators<Cart>{

    @Override
    public boolean create(Cart entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Cart getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = MSSQLConnection.getConnection();
            String query = """
                SELECT TOP 1 * FROM CART_DETAILS WHERE Cart_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                Cart cart = new Cart();
                cart.setUserId(rs.getInt("UserID"));
                cart.setFirstName(rs.getString("First_Name"));
                cart.setLastName(rs.getString("Last_Name"));
                cart.setContactNumber(rs.getString("User_Contact"));
               
                return cart;
            }

            return null;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved ingredient an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }  
    }

    @Override
    public List<Cart> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean update(Cart entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
