package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Food;
import models.Category;
import config.DBConnection;
import interfaces.IDatabaseOperators;

public class FoodService implements IDatabaseOperators<Food> {
    @Override
    public boolean create(Food food) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
              
            String query = """
                INSERT INTO FOOD (Food_Name, Price, Category_ID) VALUES (?, ?, ?)
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setInt(3, food.getCategoryId());
            
            success = pst.executeUpdate() > 0;
            
            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
            
            return success;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            //System.err.println("Error creating ingredient: " + e.getMessage());
            //JOptionPane.showMessageDialog(null, "Error creating ingredient: " + e.getMessage(),"MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public Food getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = """
               SELECT FOOD.Food_ID, FOOD.Food_Name, FOOD.Price, CATEGORY.Category_ID, CATEGORY.Category_Name
               FROM FOOD 
               INNER JOIN CATEGORY ON FOOD.Category_ID = CATEGORY.Category_ID 
               WHERE FOOD.Food_ID = ?
            """;            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {             
                return new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"), 
                    new Category(
                        rs.getInt("Category_ID"), 
                        rs.getString("Category_Name")
                    )
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
            String query = """
                SELECT FOOD.Food_ID, FOOD.Food_Name, FOOD.Price, CATEGORY.Category_ID, CATEGORY.Category_Name
                FROM FOOD 
                INNER JOIN CATEGORY ON FOOD.Category_ID = CATEGORY.Category_ID 
            """;

            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {            
                Food food = new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"), 
                    new Category(
                        rs.getInt("Category_ID"),
                        rs.getString("Category_Name")
                    )   
                );
                
                foods.add(food);
            }
            return foods;
        } finally {
            DBConnection.closeResources(rs, pst);
            if (conn != null) conn.close();
        }
    }
    
    public List<Food> getByCategory(int categoryId) throws SQLException { 
        List<Food> foods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = """
               SELECT FOOD.Food_ID, FOOD.Food_Name, FOOD.Price, CATEGORY.Category_ID, CATEGORY.Category_Name
               FROM FOOD 
               INNER JOIN CATEGORY ON FOOD.Category_ID = CATEGORY.Category_ID 
               WHERE CATEGORY.Category_ID = ?
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, categoryId);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Food food = new Food(
                    rs.getInt("Food_ID"), 
                    rs.getString("Food_Name"), 
                    rs.getDouble("Price"), 
                    new Category(
                        rs.getInt("Category_ID"),
                        rs.getString("Category_Name")
                    )   
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
            String query = """
                UPDATE FOOD SET Food_Name = ?, Price = ?, Category_ID = ? WHERE Food_ID = ?
            """;
            pst = conn.prepareStatement(query);
            
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setInt(3, food.getCategoryId());
            pst.setInt(4, food.getFoodId());
            
            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = """
                DELETE FROM FOOD WHERE Food_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            
            return pst.executeUpdate() > 0;
        } finally {
            DBConnection.closeResources(null, pst);
            if (conn != null) conn.close();
        }
    }
    
    public boolean isFoodExists(String foodName) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            String query = """
                SELECT COUNT(*) FROM FOOD WHERE UPPER(Food_Name) = UPPER(?)
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, foodName);
            rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } finally {
            DBConnection.closeResources(rs, pst);
        }
    }
}
