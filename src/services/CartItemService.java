package services;

import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.CartItem;
import models.Cart;
import models.Food;
//import models.User;

public class CartItemService implements IDatabaseOperators<CartItem>{

    @Override
    public boolean create(CartItem item) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
            
            String query = """
                    EXEC AddCart
                    @Cart_ID = ?,
                    @Food_ID = ?,
                    @Item_Quantity = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, item.getCart().getCartId());
            pst.setInt(2, item.getFood().getFoodId());
            pst.setInt(3, item.getCartItemQuantity());
            
            boolean created = pst.executeUpdate() > 0;
            if(!created) {
                throw new SQLException("An error occured during cart item creation.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to create cart item an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    
    public List<CartItem> getAllItemsInCart(int cartId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
               SELECT * FROM CART_ITEM_WITH_PRICE WHERE Cart_ID = ?
            """;            
            pst = conn.prepareStatement(query);
            pst.setInt(1, cartId);
            rs = pst.executeQuery();
            
            while (rs.next()) {       
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("CartItem_ID"));
                cartItem.setCart(new Cart(rs.getInt("Cart_ID")));
                cartItem.setFood(new Food(
                    rs.getInt("Food_ID"),
                    rs.getString("Food_Name"),
                    rs.getDouble("Price")
                ));
                cartItem.setCartItemQuantity(rs.getInt("Item_Quantity"));
                
                cartItems.add(cartItem);
            }
            
            return cartItems;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved cart items an error occured in our end.");
        } finally {
            MSSQLConnection.closeResources(rs, pst);
        } 
    }

    @Override
    public CartItem getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            
            String query = """
                SELECT TOP 1 * FROM CART_ITEM_WITH_PRICE WHERE CartItem_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("CartItem_ID"));
                cartItem.setCart(new Cart(rs.getInt("Cart_ID")));
                cartItem.setFood(new Food(
                    rs.getInt("Food_ID"),
                    rs.getString("Food_Name"),
                    rs.getDouble("Price")
                ));
                cartItem.setCartItemQuantity(rs.getInt("Item_Quantity"));
                
                return cartItem;
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
    public List<CartItem> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean update(CartItem entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public boolean updateQuantity(int cartId, int foodId, int newQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            
            String query = """
               UPDATE CART_ITEM SET Item_Quantity = ? WHERE Cart_ID  = ? AND Food_ID = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, newQuantity); 
            pst.setInt(2, cartId);  
            pst.setInt(3, foodId);  

            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during cart item updation of quantity.");
            }
            
            conn.commit();
            return true;  
             
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update cart item an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean updateQuantity(int cartItemId, int newQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            
            String query = """
               UPDATE CART_ITEM SET Item_Quantity = ? WHERE CartItem_ID  = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, newQuantity); 
            pst.setInt(2, cartItemId);    

            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during cart item updation of quantity.");
            }
            
            conn.commit();
            return true;  
             
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update cart item an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            
            String query = """
               DELETE FROM CART_ITEM WHERE CartItem_ID  = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);    

            boolean deleted = pst.executeUpdate() > 0;
            if(!deleted) {
                throw new SQLException("An error occured during cart item deletion of cart item.");
            }
            
            conn.commit();
            return true;  
             
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update cart item an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    
    public boolean deleteCartItem(int cartId, int foodId) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            
            String query = """
               EXEC DELETE_CART_ITEM
                    @Cart_ID = ?,
                    @Food_ID = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, cartId);
            pst.setInt(2, foodId);

            boolean deleted = pst.executeUpdate() > 0;
            if(!deleted) {
                throw new SQLException("An error occured during cart item deletion of cart item.");
            }
            
            conn.commit();
            return true;  
             
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update cart item an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    
    public boolean checkout(int userId, int riderId) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
            
            String query = """
               EXEC CHECKOUT
                    @UserID = ?,
                    @Rider_ID = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setInt(2, riderId);

            boolean checked = pst.executeUpdate() > 0;
            if(!checked) {
                throw new SQLException("An error occured during checkout.");
            }
            
            conn.commit();
            return true;  
             
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to checkout cart item an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
}
