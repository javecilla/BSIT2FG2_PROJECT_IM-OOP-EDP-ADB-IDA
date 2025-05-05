package test;

import controllers.FoodController;
import helpers.Response;
import models.Food;

public class UpdateFood {
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    
    public static void main(String[] args) {
        int foodId = 41;
        String foodName = "testUpdate";
        double price = 40.00;
        int categoryId = 3;
                
        Response<Food> response = FOOD_CONTROLLER.updateFood(
            foodId,
            foodName,
            price,
            categoryId
        );
        if (response.isSuccess()) {
            System.out.println(response.getMessage());
        } else {
            System.out.println(response.getMessage());
        }
    }
    
}
