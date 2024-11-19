package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Recipe;
import models.Food;
import models.Ingredient;
import config.DBConnection;
import interfaces.IDatabaseOperators;

public class RecipeService implements IDatabaseOperators<Recipe> {
    @Override
    public List<Recipe> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public boolean create(Recipe entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Recipe getById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            //get all ingredients information  base on the food id
            String query = """
                SELECT RECIPE.*, 
                       FOOD.Food_Name, 
                       FOOD.[Price], 
                       INGREDIENT.Ingredient_Name, 
                       INGREDIENT.Ingredient_Quantity, 
                       INGREDIENT.Reorder_Point
                FROM INGREDIENT 
                INNER JOIN (FOOD 
                INNER JOIN RECIPE 
                    ON FOOD.Food_ID = RECIPE.Food_ID) 
                    ON INGREDIENT.Ingredient_ID = RECIPE.Ingredient_ID
                WHERE RECIPE.Food_ID = ?;
            """;
            
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if(rs.next()) {    
                return new Recipe(
                    rs.getInt("Recipe_Quantity"), 
                    new Food(
                        rs.getInt("Food_ID"), 
                        rs.getString("Food_Name"),
                        rs.getDouble("Price")
                    ),
                    new Ingredient(
                        rs.getInt("Ingredient_ID"),
                        rs.getString("Ingredient_Name"),
                        rs.getInt("Ingredient_Quantity"),
                        rs.getInt("Reorder_Point")
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
    public boolean update(Recipe entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
