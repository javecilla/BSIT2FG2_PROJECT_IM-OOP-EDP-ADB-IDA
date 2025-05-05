package test;

import controllers.FoodController;
import helpers.Response;
import models.Food;

public class DeleteFood {
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    
    public static void main(String[] args) {
        int foodId = 41;                
        Response<String> deletedResponse = FOOD_CONTROLLER.deleteFood(foodId);
        if (deletedResponse.isSuccess()) {
            System.out.println(deletedResponse.getMessage());
        } else {
            System.out.println(deletedResponse.getMessage());
        }
    }
    
}


