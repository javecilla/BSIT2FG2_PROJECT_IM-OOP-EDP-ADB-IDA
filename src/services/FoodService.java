package services;

import models.Food;
import models.Category;
import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.IOperators;

public class FoodService implements IOperators<Food> {

    private boolean isFoodExists(Connection conn, String foodName) throws SQLException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT COUNT(*) FROM FOOD WHERE UPPER(Food_Name) = UPPER(?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, foodName);
            rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
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
            
            if (isFoodExists(conn, food.getFoodName())) {
                System.out.println("Error: Food '" + food.getFoodName() + "' already exists!");
                return false;
            }
            
            String query = "INSERT INTO FOOD (Food_Name, Price, Category_ID) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setInt(3, food.getCategory().getCategoryId()); // Use getCategoryId() directly
            
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
            pst.setInt(1, Integer.parseInt(id));
            rs = pst.executeQuery();
            
            if (rs.next()) {
                Category category = new Category(
                    rs.getInt("Category_ID"), 
                    rs.getString("Category_Name")
                );
                
                return new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"), 
                    category
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
                Category category = new Category(
                    rs.getInt("Category_ID"),
                    rs.getString("Category_Name")
                );
                
                Food food = new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"), 
                    category
                );
                
                foods.add(food);
            }
            return foods;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }
    
    public List<Food> getByCategory(String categoryName) throws SQLException { 
        List<Food> foods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT f.Food_ID, f.Food_Name, f.Price, "
                         + "c.Category_ID, c.Category_Name "
                         + "FROM FOOD f "
                         + "INNER JOIN CATEGORY c ON f.Category_ID = c.Category_ID "
                         + "WHERE c.Category_Name = ?";
            
            pst = conn.prepareStatement(query);
            pst.setString(1, categoryName);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("Category_ID"),
                    rs.getString("Category_Name")
                );
                
                Food food = new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"), 
                    category
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
            pst.setInt(3, food.getCategory().getCategoryId());
            pst.setInt(4, food.getFoodId());
            
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
            pst.setInt(1, Integer.parseInt(id));
            
            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
}
