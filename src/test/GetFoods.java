package test;

import controllers.FoodController;
import helpers.Response;
import java.util.List;
import models.Food;

public class GetFoods {
    protected static final FoodController FOOD_CONTROLLER = new FoodController();
    
    public static void main(String[] args) {                
        Response<List<Food>> response = FOOD_CONTROLLER.getAllFoods();
        if (response.isSuccess()) {
            List<Food> foods = response.getData();
            
            System.out.printf("\n%-10s %-30s %-10s %-20s%n", "Food ID", "Name", "Price", "Category");
            if(foods.isEmpty()) {
                System.out.println("No record is found.");
            }
            for (Food food : foods) {
                System.out.printf("%-10s %-30s $%-10s %-20s%n", 
                    food.getFoodId(),
                    food.getFoodName(),
                    food.getPrice(),
                    food.getCategoryName()
                );
            }
            System.out.println("Total Records: " + foods.size());
        } else {
            System.out.println("Error: " + response.getMessage());
        }
    }
}
