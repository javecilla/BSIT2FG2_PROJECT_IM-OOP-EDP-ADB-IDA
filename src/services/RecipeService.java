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
import config.MSACCESSConnection;
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
            conn = MSACCESSConnection.getConnection();
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
            MSACCESSConnection.closeResources(rs, pst);
            //if (conn != null) conn.close();
        } 
    }
    
    public Recipe getAllIngredientsInFood(int foodId) throws SQLException {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        conn = MSACCESSConnection.getConnection();
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
        pst.setInt(1, foodId);
        rs = pst.executeQuery();

        Recipe recipe = null;
        List<Ingredient> ingredients = new ArrayList<>();

        // Loop through the result set to populate the recipe and ingredients
        while(rs.next()) {
            if (recipe == null) {
                // Initialize the recipe object only once
                recipe = new Recipe();
                recipe.setRecipeQuantity(rs.getInt("Recipe_Quantity"));
                Food food = new Food();
                food.setFoodId(rs.getInt("Food_ID"));
                food.setFoodName(rs.getString("Food_Name"));
                food.setPrice(rs.getDouble("Price")); // assuming price is a double type
                recipe.setFood(food);
            }

            // Create and add ingredients to the list
            Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(rs.getInt("Ingredient_ID"));
            ingredient.setIngredientName(rs.getString("Ingredient_Name"));
            ingredient.setQuantity(rs.getInt("Ingredient_Quantity"));
            ingredient.setReorderPoint(rs.getInt("Reorder_Point"));

            ingredients.add(ingredient);
        }

        // Set the list of ingredients to the recipe
        if (recipe != null) {
            recipe.setIngredients(ingredients);
        }

        return recipe;
    } finally {
        MSACCESSConnection.closeResources(rs, pst);
        //if (conn != null) conn.close();
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
