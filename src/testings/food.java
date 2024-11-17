
package testings;

import models.Food;
import controllers.FoodController;
import helpers.Response;
import java.util.List;

public class food {
    public static void main(String[] args) {
        FoodController controller = new FoodController();
        // 1. ADD FOOD
//        Response<Food> addFoodResponse = controller.addFood("Test", 12.99, 1);
//        if (addFoodResponse.isSuccess()) {
//            System.out.println("Food added successfully: " + addFoodResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error adding food: " + addFoodResponse.getMessage());
//        }

        // 2. GET ALL FOODS
        Response<List<Food>> foodsResponse = controller.getAllFoods();
        if (foodsResponse.isSuccess()) {
            List<Food> foods = foodsResponse.getData();
            System.out.printf("\n\n%-10s %-30s %-10s %-20s%n", "Food ID", "Food Name", "Price", "Category");
            double totalPrice = 0.0;
            for (Food food : foods) {
                //System.out.println(food.getFoodId() + ".\t" + food.getFoodName());
                food.displayInfo();
                //eto sample pano mag calculate ng price if 
                //yung data is naka loop yung pag fetch/render
                totalPrice += food.getPrice();
            }
            System.out.println("Total Records: " + foods.size());
            System.out.println("Total Price: " + totalPrice);
        } else {
            System.out.println("Error: " + foodsResponse.getMessage());
        }
        
        //3.  GET ALL FOODS BY CATEGORY
        //dito naman yung pag filter ng food base sa category
        //lalabas yung mga foods depende sa parameter na category
//        Response<List<Food>> foodsByCategoryResponse = controller.getFoodsByCategory("Sandwiches");
//        if (foodsByCategoryResponse.isSuccess()) {
//            List<Food> foods = foodsByCategoryResponse.getData();
//            System.out.printf("\n\n%-10s %-30s %-10s %-20s%n", "Food ID", "Food Name", "Price", "Category");       
//            for (Food food : foods) {
//                //System.out.println(food.getFoodId() + ".\t" + food.getFoodName());
//                food.displayInfo();
//                
//            }
//            System.out.println("Total Records: " + foods.size());
//        } else {
//            System.out.println("Error: " + foodsByCategoryResponse.getMessage());
//        }

        // 5. GET FOOD BY ID
//        Response<Food> getFoodByIdResponse = controller.getFoodById(10);
//        if (getFoodByIdResponse.isSuccess()) {
//            Food food = getFoodByIdResponse.getData();
//            System.out.println("Food retrieved: " + food.getFoodName());
//        } else {
//            System.out.println("Error retrieving food: " + getFoodByIdResponse.getMessage());
//        }

        // 6. UPDATE FOOD
//        Response<Food> updateFoodResponse = controller.updateFood(10, "Updated Test", 10.99, 2);
//        if (updateFoodResponse.isSuccess()) {
//            System.out.println("Food updated: " + updateFoodResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error updating food: " + updateFoodResponse.getMessage());
//        }

        // 7. DELETE FOOD
//        Response<String> deleteFoodResponse = controller.deleteFood(10);
//        if (deleteFoodResponse.isSuccess()) {
//            System.out.println("Food deleted successfully");
//        } else {
//            System.out.println("Error deleting food: " + deleteFoodResponse.getMessage());
//        }
    }
}
