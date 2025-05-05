package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class UpdateReorderPointIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) {
        int ingredientId = 39;
        //pag magbabawas ng re-order point make it negative, positive pag increase
        int decreaseROP = -5;
        int increaseROP = 10;
                
        Response<Ingredient> updateResponse = INGREDIENT_CONTROLLER.updateReorderPoints(
            ingredientId,
            increaseROP
        );
        if (updateResponse.isSuccess()) {
            System.out.println(updateResponse.getMessage());
        } else {
            System.out.println(updateResponse.getMessage());
        }
    }
    
}


