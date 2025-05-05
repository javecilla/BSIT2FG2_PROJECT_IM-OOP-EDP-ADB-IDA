package test;

import controllers.IngredientController;
import helpers.Response;
import models.Ingredient;

public class CreateIngredient {
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    
    public static void main(String[] args) {
        String ingredientName = "test";
        int quantity = 30;
        int reorderPoint = 15;
        int supplierId = 2;
        int adminId = 1;
                
        Response<Ingredient> createResponse = INGREDIENT_CONTROLLER.addIngredient(
            ingredientName,
            quantity,
            reorderPoint,
            supplierId,
            adminId
        );
        if (createResponse.isSuccess()) {
            System.out.println(createResponse.getMessage());
        } else {
            System.out.println(createResponse.getMessage());
        }
    }
    
}
