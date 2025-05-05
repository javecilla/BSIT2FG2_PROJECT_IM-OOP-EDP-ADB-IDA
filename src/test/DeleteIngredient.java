package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class DeleteIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) {
        int ingredientId = 39;                
        Response<Boolean> deletedResponse = INGREDIENT_CONTROLLER.deleteIngredient(ingredientId);
        if (deletedResponse.isSuccess()) {
            System.out.println(deletedResponse.getMessage());
        } else {
            System.out.println(deletedResponse.getMessage());
        }
    }
    
}


