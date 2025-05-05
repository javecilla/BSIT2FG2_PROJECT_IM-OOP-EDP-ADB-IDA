package controllers;

import helpers.Response;
import models.Recipe;
import services.RecipeService;

public class RecipeController {
    private final RecipeService recipeService;
    
    public RecipeController() {
        this.recipeService = new RecipeService();
    }
    
    public Response<Recipe> getAllIngredientsInFood(int foodId) {
        try {
            Recipe recipe = recipeService.getAllIngredientsInFood(foodId);

            if (recipe == null || recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
                return Response.error("No ingredients found for Food ID: " + foodId);
            }

            return Response.success("Ingredients retrieved successfully", recipe);
        } catch (Exception e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
}