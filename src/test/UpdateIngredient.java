package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class UpdateIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) {
        int ingredientId = 39;
        String ingredientName = "testUpdated";
        int quantity = 70;
        int reorderPoint = 40;
        int supplierId = 2;
        int adminId = 1;
                
        Response<Ingredient> updateResponse = INGREDIENT_CONTROLLER.updateIngredient(
            ingredientId,
            ingredientName,
            quantity,
            reorderPoint,
            supplierId,
            adminId
        );
        if (updateResponse.isSuccess()) {
            System.out.println(updateResponse.getMessage());
        } else {
            System.out.println(updateResponse.getMessage());
        }
    }
    
}
