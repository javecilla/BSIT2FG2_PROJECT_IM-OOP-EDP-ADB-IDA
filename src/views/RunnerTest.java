
package views;

import models.Food;
import controllers.FoodController;
import helpers.Response;
import java.util.List;

public class RunnerTest {
    public static void main(String[] args) {
        FoodController controller = new FoodController();
        
        // GET ALL FOODS
        Response<List<Food>> foodsResponse = controller.getAllFoods();
        if (foodsResponse.isSuccess()) {
            List<Food> foods = foodsResponse.getData();
            System.out.println("=======\n\nTotal foods found: " + foods.size());
            for (Food food : foods) {
                System.out.println(food.getFoodId() + ".\t" + food.getFoodName());
            }
        } else {
            System.out.println("Error: " + foodsResponse.getMessage());
        }
        
        // GET ONE FOOD BY ID
//        Response<Food> foodResponse = controller.getFoodById("1");
//        if (foodResponse.isSuccess()) {
//            Food food = foodResponse.getData();
//            System.out.println("Food Id: " + food.getFoodId());
//            System.out.println("Food Name: " + food.getFoodName());
//            System.out.println("Price: " + food.getPrice());
//            System.out.println("Category: " + food.getCategoryName() + "\n\n=======");
//        } else {
//            System.out.println("Error: " + foodResponse.getMessage());
//        }

        // ADD NEW FOOD
        Response<Food> createResponse = controller.addFood("Turon", 20.00, "2");
        if (createResponse.isSuccess()) {
            System.out.println("Success: " + createResponse.getMessage());
            Food createdFood = createResponse.getData();
            if (createdFood != null) {
                System.out.println("Created food: " + createdFood.getFoodName());
            }
        } else {
            System.out.println("Error: " + createResponse.getMessage());
        }
         
        // UPDATE FOOD
//        Food foodToUpdate = new Food();
//        foodToUpdate.setFoodId("4");
//        foodToUpdate.setFoodName("Updated Pizza");
//        foodToUpdate.setPrice(11.99);
//        foodToUpdate.setCategoryId("2");
//
//        Response<Food> updateResponse = controller.updateFood(foodToUpdate);
//        if (updateResponse.isSuccess()) {
//            System.out.println("Update Success: " + updateResponse.getMessage());
//            Food updatedFood = updateResponse.getData();
//            System.out.println("Updated food name: " + updatedFood.getFoodName());
//            System.out.println("Updated price: $" + updatedFood.getPrice());
//        } else {
//            System.out.println("Update Error: " + updateResponse.getMessage());
//        }

        // DELETE FOOD
//        Response<String> deleteResponse = controller.deleteFood("5");
//        if (deleteResponse.isSuccess()) {
//            System.out.println("Delete Success: " + deleteResponse.getMessage());
//        } else {
//            System.out.println("Delete Error: " + deleteResponse.getMessage());
//        }
    }
}
