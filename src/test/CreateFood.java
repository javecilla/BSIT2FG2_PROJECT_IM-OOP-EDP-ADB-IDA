package test;

import controllers.FoodController;
import helpers.Response;
import models.Food;

public class CreateFood {
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    
    public static void main(String[] args) {
        String foodName = "test";
        double price = 30.00;
        int categoryId = 3;
                
        Response<Food> response = FOOD_CONTROLLER.addFood(
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
