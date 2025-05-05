package test;

import controllers.RecipeController;
import helpers.Response;
import models.Ingredient;
import models.Recipe;

public class GetAllIngredientsInFood {
    protected static final RecipeController RECIPE_CONTROLLER = new RecipeController();
    
    public static void main(String[] args) {
        int foodId = 2;
        Response<Recipe> response = RECIPE_CONTROLLER.getAllIngredientsInFood(foodId);
        
        if (response.isSuccess()) {
            System.out.println(response.getMessage());
            Recipe recipe = response.getData();
            if (recipe != null && recipe.getIngredients() != null) {
                System.out.println("Food: " + recipe.getFood().getFoodName() + " (Price: " + recipe.getFood().getPrice() + ")");
                System.out.println("Recipe Quantity: " + recipe.getRecipeQuantity());
                System.out.println("Ingredients:");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    System.out.println("- " + ingredient.getIngredientName() + 
                                       " (Quantity: " + ingredient.getQuantity() + 
                                       ", Reorder Point: " + ingredient.getReorderPoint() + ")");
                }
            } else {
                System.out.println("No ingredients found for Food ID: " + foodId);
            }
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}