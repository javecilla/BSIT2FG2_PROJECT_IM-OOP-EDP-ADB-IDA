package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class CheckReorderIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) { 
        int ingredientId = 2;
        Response<String> ingredientResponse = INGREDIENT_CONTROLLER.checkReorderNeed(ingredientId);
        if (ingredientResponse.isSuccess()) {
            System.out.println(ingredientResponse.getMessage());
        } else {
            System.out.println("Error: " + ingredientResponse.getMessage());
        }
    }
}

