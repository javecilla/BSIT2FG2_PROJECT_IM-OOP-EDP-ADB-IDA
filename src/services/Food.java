package services;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Food {
    private String foodId;
    private String foodName;
    private double price;
    
    public Food() {}
    
    public Food(String foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }
    
    //Getters and Setters
    public String getFoodId() { return foodId; }
    public void setFoodId(String foodId) { this.foodId = foodId; }
    
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    //Database Operations
    public List<Food> getAllFoods() throws SQLException {
        List<Food> foods = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT * FROM FOOD";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                Food food = new Food(
                    rs.getString("FoodID"),
                    rs.getString("FoodName"),
                    rs.getDouble("Price")
                );
                foods.add(food);
            }
        } finally {
            DBConnection.closeResources(rs, pst);
        }
        
        return foods;
    }
    
    public Food getFoodById(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Food food = null;
        
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT * FROM FOOD WHERE FoodID = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                food = new Food(
                    rs.getString("FoodID"),
                    rs.getString("FoodName"),
                    rs.getDouble("Price")
                );
            }
        } finally {
            DBConnection.closeResources(rs, pst);
        }
        
        return food;
    }

}