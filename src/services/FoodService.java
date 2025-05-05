package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Food;
import models.Category;
import config.MSACCESSConnection;
import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import java.util.Arrays;

public class FoodService implements IDatabaseOperators<Food> {
    @Override
    public boolean create(Food food) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            conn.setAutoCommit(false);
              
            String query = """
                INSERT INTO FOOD (Food_Name, Price, Category_ID) VALUES (?, ?, ?)
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setInt(3, food.getCategoryId());
            
            boolean created = pst.executeUpdate() > 0;
            if(!created) {
                throw new SQLException("An error occured during food creation.");
            }
            
            conn.commit();
            return true;  
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to registered an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    @Override
    public Food getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//               SELECT FOOD.Food_ID, FOOD.Food_Name, FOOD.Price, CATEGORY.Category_ID, CATEGORY.Category_Name
//               FROM FOOD 
//               INNER JOIN CATEGORY ON FOOD.Category_ID = CATEGORY.Category_ID 
//               WHERE FOOD.Food_ID = ?
//            """;            

            String query = """
               SELECT TOP 1 * FROM FOOD_DETAILS WHERE Food_ID = ?; 
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
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved food an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        } 
    }
    
    @Override
    public List<Food> getAll() throws SQLException {
        List<Food> foods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//                SELECT FOOD.Food_ID, FOOD.Food_Name, FOOD.Price, CATEGORY.Category_ID, CATEGORY.Category_Name
//                FROM FOOD 
//                INNER JOIN CATEGORY ON FOOD.Category_ID = CATEGORY.Category_ID 
//            """;
            String query = """
               SELECT * FROM FOOD_DETAILS ORDER BY Food_ID DESC; 
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
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved foods an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        } 
    }
    
    public List<Food> getByCategory(int categoryId) throws SQLException { 
        List<Food> foods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//               SELECT FOOD.Food_ID, FOOD.Food_Name, FOOD.Price, CATEGORY.Category_ID, CATEGORY.Category_Name
//               FROM FOOD 
//               INNER JOIN CATEGORY ON FOOD.Category_ID = CATEGORY.Category_ID 
//               WHERE CATEGORY.Category_ID = ?
//            """;

            String query = """
               SELECT * FROM FOOD_DETAILS WHERE Category_ID = ?
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
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved foods an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        } 
    }
    
    @Override
    public boolean update(Food food) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            String query = """
                UPDATE FOOD SET Food_Name = ?, Price = ?, Category_ID = ? WHERE Food_ID = ?
            """;
            pst = conn.prepareStatement(query);
            
            pst.setString(1, food.getFoodName());
            pst.setDouble(2, food.getPrice());
            pst.setInt(3, food.getCategoryId());
            pst.setInt(4, food.getFoodId());
            
            boolean updated = pst.executeUpdate() > 0;
            if(!updated) {
                throw new SQLException("An error occured during food updation.");
            }
            
            conn.commit();
            return true;
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to update food an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
            String query = """
                DELETE FROM FOOD WHERE Food_ID = ?
            """;
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            
            boolean deleted = pst.executeUpdate() > 0;
            if(!deleted) {
                throw new SQLException("An error occured during food deletion.");
            }
            
            conn.commit();
            return true;
        } catch(SQLException e) {
            if (conn != null) conn.rollback();
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to delete food an error occured in our end.");
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            //if (conn != null) conn.close();
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        }
    }
    
    public boolean isFoodNameExist(String foodName) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = MSSQLConnection.getConnection();
            String query = """
                SELECT TOP 1 Food_Name FROM FOOD WHERE UPPER(Food_Name) = UPPER(?);
            """;
            pst = conn.prepareStatement(query);
            pst.setString(1, foodName);
            rs = pst.executeQuery();
            return rs.next();
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to checked food name availability an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
        } 
    }
}