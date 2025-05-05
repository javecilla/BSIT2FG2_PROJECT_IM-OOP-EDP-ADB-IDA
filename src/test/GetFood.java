package test;

import controllers.FoodController;
import helpers.Response;
import models.Food;

public class GetFood {
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    
    public static void main(String[] args) { 
        int foodId = 3;
        Response<Food> response = FOOD_CONTROLLER.getFoodById(foodId);
        if (response.isSuccess()) {
            Food food = response.getData();

            System.out.println("Food ID: " + food.getFoodId());
            System.out.println("Food Name: " + food.getFoodName());
            System.out.println("Price: " + food.getPrice());
            System.out.println("Category: " + food.getCategoryName());
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}

