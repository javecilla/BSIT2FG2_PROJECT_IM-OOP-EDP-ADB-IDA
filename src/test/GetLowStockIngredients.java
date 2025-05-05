package test;

import controllers.IngredientController;
import helpers.Response;
import java.util.List;
import models.Ingredient;

public class GetLowStockIngredients {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) {                
        Response<List<Ingredient>> ingredientResponse = INGREDIENT_CONTROLLER.getAllIngredientsLowStocks();
        if (ingredientResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientResponse.getData();
            
            System.out.printf("\n%-10s %-30s %-10s %-20s%n", "Ingredient ID", "Name", "Current Stock", "Reorder Point");
            if(ingredients.isEmpty()) {
                System.out.println("No record is found.");
            }
            for (Ingredient ingredient : ingredients) {
                System.out.printf("%-10s %-30s $%-10s %-20s%n", 
                    ingredient.getIngredientId(), 
                    ingredient.getIngredientName(), 
                    ingredient.getQuantity(), 
                    ingredient.getReorderPoint()
                );
            }
            System.out.println("Total Records: " + ingredients.size());
        } else {
            System.out.println("Error: " + ingredientResponse.getMessage());
        }
    }
}
