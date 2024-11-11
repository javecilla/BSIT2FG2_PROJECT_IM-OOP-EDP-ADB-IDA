package views;

import models.Food;
import controllers.FoodController;
import helpers.Response;
import java.util.List;

public class RunnerTest {
    public static void main(String[] args) {
        FoodController controller = new FoodController();

        // 1. ADD RECORD
//        Response<Food> addFoodResponse = controller.addFood("Test", 12.99, 1);
//        if (addFoodResponse.isSuccess()) {
//            System.out.println("Food added successfully: " + addFoodResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error adding food: " + addFoodResponse.getMessage());
//        }

        // 2. GET ALL RECORDS
        Response<List<Food>> foodsResponse = controller.getAllFoods();
        if (foodsResponse.isSuccess()) {
            List<Food> foods = foodsResponse.getData();
            System.out.printf("\n\n%-10s %-30s %-10s %-20s%n", "Food ID", "Food Name", "Price", "Category");
            for (Food food : foods) {
                //System.out.println(food.getFoodId() + ".\t" + food.getFoodName());
                food.displayInfo();
            }
            System.out.println("Total Records: " + foods.size());
        } else {
            System.out.println("Error: " + foodsResponse.getMessage());
        }
        
        //3.  GET ALL RECORDS BY CATEGORY
//        Response<List<Food>> foodsByCategoryResponse = controller.getFoodsByCategory("Sandwiches");
//        if (foodsByCategoryResponse.isSuccess()) {
//            List<Food> foods = foodsByCategoryResponse.getData();
//            System.out.printf("\n\n%-10s %-30s %-10s %-20s%n", "Food ID", "Food Name", "Price", "Category");
//            for (Food food : foods) {
//                food.displayInfo();
//            }
//            System.out.println("Total Records: " + foods.size());
//        } else {
//            System.out.println("Error: " + foodsByCategoryResponse.getMessage());
//        }

        // 5. GET RECORD BY ID
//        Response<Food> getFoodByIdResponse = controller.getFoodById(7);
//        if (getFoodByIdResponse.isSuccess()) {
//            System.out.println("Food retrieved: " + getFoodByIdResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error retrieving food: " + getFoodByIdResponse.getMessage());
//        }

        // 6. UPDATE RECORD
//        Response<Food> updateFoodResponse = controller.updateFood(4, "Spaghetti", 10.99, 2);
//        if (updateFoodResponse.isSuccess()) {
//            System.out.println("Food updated: " + updateFoodResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error updating food: " + updateFoodResponse.getMessage());
//        }

        // 7. DELETE RECORD
//        Response<String> deleteFoodResponse = controller.deleteFood(8);
//        if (deleteFoodResponse.isSuccess()) {
//            System.out.println("Food deleted successfully");
//        } else {
//            System.out.println("Error deleting food: " + deleteFoodResponse.getMessage());
//        }
    }
}
