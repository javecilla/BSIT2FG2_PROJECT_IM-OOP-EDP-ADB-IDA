package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class UpdateQuantityIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) {
        int ingredientId = 39;
        //pag magbabawas ng quantity make it negative, positive pag increase
        int decreaseQuantity = -20;
        int increaseQuantity = 70;
                
        Response<Ingredient> updateResponse = INGREDIENT_CONTROLLER.updateQuantity(
            ingredientId,
            increaseQuantity
        );
        if (updateResponse.isSuccess()) {
            System.out.println(updateResponse.getMessage());
        } else {
            System.out.println(updateResponse.getMessage());
        }
    }
    
}

