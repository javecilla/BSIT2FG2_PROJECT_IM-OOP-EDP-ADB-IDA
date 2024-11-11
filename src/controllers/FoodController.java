package controllers;

import models.Food;
import services.FoodService;
import helpers.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

public class FoodController {
    protected final FoodService foodService;
    
    public FoodController() {
        this.foodService = new FoodService();
    }
    
    // Create a new food item
    public Response<Food> addFood(String foodName, double price, String categoryId) {
        if(foodName == null || foodName.trim().isEmpty()) {
            return Response.error("Food name cannot be empty!");
        }
        
        if(price <= 0) {
            return Response.error("Price must be greater than 0!");
        }
        
        if(categoryId == null || categoryId.trim().isEmpty()) {
            return Response.error("Category ID cannot be empty");
        }
        
        try {
            Food newFood = new Food();
            newFood.setFoodName(foodName.trim());
            newFood.setPrice(price);
            newFood.setCategoryId(categoryId.trim());
          
            if(foodService.create(newFood)) {
                return Response.success("Food created successfully!", newFood);
            } else {
                return Response.error("Failed to create food.");
            }
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Get all foods
    public Response<List<Food>> getAllFoods() {
        try {
            List<Food> foods = foodService.getAll();
            
            if(foods == null || foods.isEmpty()) {
                return Response.success("No foods found", Collections.emptyList());
            }
            
            return Response.success("Foods retrieved successfully", foods);
        } catch(SQLException e) {
            return Response.error("Failed to retrieve foods: " + e.getMessage());
        }
    }
    
    // Get food by ID
    public Response<Food> getFoodById(String id) {
        if(id == null || id.trim().isEmpty()) {
            return Response.error("Food ID cannot be empty");
        }

        try {
            Food food = foodService.getById(id);
            
            if(food == null) {
                return Response.error("Food not found with ID: " + id);
            }
            
            return Response.success("Food retrieved successfully", food);
        } catch(SQLException e) {
            return Response.error("Failed to retrieve food: " + e.getMessage());
        }
    }
    
    // Update food
    public Response<Food> updateFood(Food food) {
        if(food == null) {
            return Response.error("Food object cannot be null");
        }

        if(food.getFoodId() == null || food.getFoodId().trim().isEmpty()) {
            return Response.error("Food ID cannot be empty");
        }

        if(food.getFoodName() == null || food.getFoodName().trim().isEmpty()) {
            return Response.error("Food name cannot be empty");
        }

        if(food.getPrice() <= 0) {
            return Response.error("Price must be greater than 0");
        }
        
        try {
            // Check if the food exists
            Food existingFood = foodService.getById(food.getFoodId());
            if(existingFood == null) {
                return Response.error("Food not found with ID: " + food.getFoodId());
            }

            if(foodService.update(food)) {
                return Response.success("Food updated successfully", food);
            } else {
                return Response.error("Failed to update food");
            }
        } catch (SQLException e) {
            return Response.error("Database error while updating food: " + e.getMessage());
        }
    }
    
    // Delete food
    public Response<String> deleteFood(String id) {
        if(id == null || id.trim().isEmpty()) {
            return Response.error("Food ID cannot be empty");
        }
        
        try {
            // Check if the food exists
            Food existingFood = foodService.getById(id);
            if (existingFood == null) {
                return Response.error("Food not found with ID: " + id);
            }

            if(foodService.delete(id)) {
                return Response.success("Food deleted successfully", null);
            } else {
                return Response.error("Failed to delete food");
            }
        } catch(SQLException e) {
            return Response.error("Database error while deleting food: " + e.getMessage());
        }
    }
    
    //ETO SAMPLE USAGE PER METHODS (Bahala kana pano mo iu use)
        public static void main(String[] args) {
//        FoodController controller = new FoodController();
        
        // GET ALL FOODS
//        Response<List<Food>> foodsResponse = controller.getAllFoods();
//        if (foodsResponse.isSuccess()) {
//            List<Food> foods = foodsResponse.getData();
//            System.out.println("=======\n\nTotal foods found: " + foods.size());
//            for (Food food : foods) {
//                System.out.println(food.getFoodId() + ".\t" + food.getFoodName());
//            }
//        } else {
//            System.out.println("Error: " + foodsResponse.getMessage());
//        }
 
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
//        Response<Food> createResponse = controller.addFood("Turon", 20.00, "2");
//        if (createResponse.isSuccess()) {
//            System.out.println("Success: " + createResponse.getMessage());
//            Food createdFood = createResponse.getData();
//            if (createdFood != null) {
//                System.out.println("Created food: " + createdFood.getFoodName());
//            }
//        } else {
//            System.out.println("Error: " + createResponse.getMessage());
//        }
        
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