package services;

import models.Food;
import interfaces.ICRUD;
import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodService implements ICRUD<Food> {
    private boolean isFoodExists(Connection conn, String foodName) throws SQLException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT COUNT(*) FROM FOOD WHERE UPPER(Food_Name) = UPPER(?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, foodName);
            
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }
    
    @Override
    public boolean create(Food food) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            if(isFoodExists(conn, food.getFoodName())) {
                System.out.println("Error: Food '" + food.getFoodName() + "' already exists!");
                return false;
            }
            
            String query = "INSERT INTO FOOD (Food_Name, Price, Category_ID) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setString(3, food.getCategoryId()); 
            
            success = pst.executeUpdate() > 0;
            
            if (success) {
                // If insert was successful, commit the transaction
                conn.commit();
            } else {
                // If insert failed, rollback
                conn.rollback();
            }
            
            return success;
        } catch (SQLException e) {
            // If there's any error, rollback
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error rolling back: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    // Reset auto-commit to true before closing
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println("Error resetting auto-commit: " + e.getMessage());
                }
            }
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public Food getById(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT f.Food_ID, f.Food_Name, f.Price, "
                    + "c.Category_ID, c.Category_Name "
                    + "FROM FOOD f "
                    + "INNER JOIN CATEGORY c ON f.Category_ID = c.Category_ID "
                    + "WHERE f.Food_ID = ?";
            
            pst = conn.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                return new Food(
                    rs.getString("Food_ID"),
                    rs.getString("Food_Name"),
                    rs.getDouble("Price"),
                    rs.getString("Category_Name")
                );
            }
            return null;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public List<Food> getAll() throws SQLException {
        List<Food> foods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT f.Food_ID, f.Food_Name, f.Price, "
                    + "c.Category_ID, c.Category_Name "
                    + "FROM FOOD f "
                    + "INNER JOIN CATEGORY c ON f.Category_ID = c.Category_ID";
            
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Food food = new Food(
                    rs.getString("Food_ID"),
                    rs.getString("Food_Name"),
                    rs.getDouble("Price"),
                    rs.getString("Category_Name")
                );
                foods.add(food);
            }
            return foods;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public boolean update(Food food) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "UPDATE FOOD SET Food_Name = ?, Price = ?, Category_ID = ? WHERE Food_ID = ?";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setString(3, food.getCategoryId()); // Assuming you've added categoryId field
            pst.setString(4, food.getFoodId());
            
            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public boolean delete(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "DELETE FROM FOOD WHERE Food_ID = ?";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, id);
            
            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
}