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
//import config.MSACCESSConnection;
import config.MSSQLConnection;
import interfaces.IDatabaseOperators;
import java.util.Arrays;

public class RecipeService implements IDatabaseOperators<Recipe> {
    @Override
    public boolean create(Recipe entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public List<Recipe> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public Recipe getById(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public Recipe getAllIngredientsInFood(int foodId) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
    
        try {
            //conn = MSACCESSConnection.getConnection();
            conn = MSSQLConnection.getConnection();
//            String query = """
//                SELECT RECIPE.*, 
//                    FOOD.Food_Name, 
//                    FOOD.[Price], 
//                    INGREDIENT.Ingredient_Name, 
//                    INGREDIENT.Ingredient_Quantity, 
//                    INGREDIENT.Reorder_Point
//             FROM INGREDIENT 
//             INNER JOIN (FOOD 
//             INNER JOIN RECIPE 
//                 ON FOOD.Food_ID = RECIPE.Food_ID) 
//                 ON INGREDIENT.Ingredient_ID = RECIPE.Ingredient_ID
//             WHERE RECIPE.Food_ID = ?;
//            """;

            String query = """
               SELECT * FROM RECIPE_DETAILS WHERE Food_ID = ?;
            """;

            pst = conn.prepareStatement(query);
            pst.setInt(1, foodId);
            rs = pst.executeQuery();

            Recipe recipe = null;
            
            while(rs.next()) {
                if (recipe == null) {
                    Food food = new Food();
                    food.setFoodId(rs.getInt("Food_ID"));
                    food.setFoodName(rs.getString("Food_Name"));
                    food.setPrice(rs.getDouble("Food_Price")); 
                    
                    recipe = new Recipe();
                    recipe.setRecipeQuantity(rs.getInt("Recipe_Quantity"));
                    recipe.setFood(food);
                }

                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(rs.getInt("Ingredient_ID"));
                ingredient.setIngredientName(rs.getString("Ingredient_Name"));
                ingredient.setQuantity(rs.getInt("Ingredient_Quantity"));
                ingredient.setReorderPoint(rs.getInt("Reorder_Point"));

                ingredients.add(ingredient);
            }

            if (recipe != null) {
                recipe.setIngredients(ingredients);
            }

            return recipe;
        } catch(SQLException e) {
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Message: " + e.getMessage());
            throw new SQLException("Failed to retrieved ingredients an error occured in our end.");
        } finally {
            //MSACCESSConnection.closeResources(rs, pst);
            MSSQLConnection.closeResources(rs, pst);
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
