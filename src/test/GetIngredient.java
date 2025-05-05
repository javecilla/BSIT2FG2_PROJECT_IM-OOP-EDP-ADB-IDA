package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class GetIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) { 
        int ingredientId = 3;
        Response<Ingredient> ingredientResponse = INGREDIENT_CONTROLLER.getIngredientById(ingredientId);
        if (ingredientResponse.isSuccess()) {
            Ingredient ingredients = ingredientResponse.getData();

            System.out.println("Ingredient ID: " + ingredients.getIngredientId());
            System.out.println("Ingredient Name: " + ingredients.getIngredientName());
            System.out.println("Ingredient Quantity: " + ingredients.getQuantity());
            System.out.println("Reorder Point: " + ingredients.getReorderPoint());
            System.out.println("Supplier ID: " + ingredients.getSupplier().getSupplierId());
            System.out.println("Admin ID: " + ingredients.getUserAdmin().getUserId());
        } else {
            System.out.println("Error: " + ingredientResponse.getMessage());
        }
    }
}

