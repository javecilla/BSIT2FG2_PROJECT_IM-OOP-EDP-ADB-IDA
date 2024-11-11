package controllers;

import models.Food;
import models.Category;
import services.FoodService;
import helpers.Response;
import interfaces.IOperatorsValidators;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

public class FoodController implements IOperatorsValidators<Food> {
    protected final FoodService foodService;
    
    public FoodController() {
        this.foodService = new FoodService();
    }
    
    // Create a new food item
    public Response<Food> addFood(String foodName, double price, int categoryId) {
        // Create a Food object and assign values
        Food newFood = new Food();
        newFood.setFoodName(foodName.trim());
        newFood.setPrice(price);
        
        Category category = new Category();
        category.setCategory(categoryId);
        newFood.setCategory(category);
        
        // Validate Food data
        Response<Food> validationResponse = validateCreate(newFood);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        // Proceed with creation if validation passes
        try { 
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
    
    public Response<List<Food>> getFoodsByCategory(String categoryName) {
        try {
            List<Food> foods = foodService.getByCategory(categoryName);
            
            if(foods == null || foods.isEmpty()) {
                return Response.success("No foods found", Collections.emptyList());
            }
            
            return Response.success("Foods retrieved successfully", foods);
        } catch(SQLException e) {
            return Response.error("Failed to retrieve foods: " + e.getMessage());
        }
    }
    
    // Get food by ID
    public Response<Food> getFoodById(int id) {
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
    public Response<Food> updateFood(int foodId, String foodName, double price, int categoryId) {
        // Create a new Food object with updated details
        Category category = new Category(categoryId, null);
        Food updatedFood = new Food(foodId, foodName.trim(), price, category);
        
        // Validate Food data for update
        Response<Food> validationResponse = validateUpdate(updatedFood);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        try {
            // Check if the food exists 
            Food existingFood = foodService.getById(foodId);
            if(existingFood == null) {
                return Response.error("Food not found with ID: " + foodId);
            }
        
            if(foodService.update(updatedFood)) {
                return Response.success("Food updated successfully", updatedFood);
            } else {
                return Response.error("Failed to update food");
            }
        } catch (SQLException e) {
            return Response.error("Database error while updating food: " + e.getMessage());
        }
    }
    
    // Delete food
    public Response<String> deleteFood(int id) {
        try {
            // Validate delete operation
            Response<Boolean> validationResponse = validateDelete(id);
            if (!validationResponse.isSuccess()) {
                return Response.error("Validation failed: " + validationResponse.getMessage());
            }
            
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
    
    @Override
    public Response<Food> validateCreate(Food food) {
        if(food.getFoodName() == null || food.getFoodName().trim().isEmpty()) {
            return Response.error("Food name cannot be empty!");
        }
        
        if(food.getPrice() <= 0) {
            return Response.error("Price must be greater than 0!");
        }
        
        if(food.getCategory() == null || food.getCategory().getCategoryId() <= 0) {
            return Response.error("Category ID must be valid!");
        }
        
        return Response.success("Validation passed", food);
    }
    
    @Override
    public Response<Food> validateUpdate(Food food) {
        if(food.getFoodName() == null || food.getFoodName().trim().isEmpty()) {
            return Response.error("Food name cannot be empty");
        }

        if(food.getPrice() <= 0) {
            return Response.error("Price must be greater than 0");
        }
        
        return Response.success("Validation passed", food);
    }
    
    @Override
    public Response<Boolean> validateDelete(int id) {
        if (id <= 0) {
            return Response.error("Invalid ID!");
        }
        return Response.success("Validation passed", true);
    }
    
    /*** SAMPLE USAGE IN EACH METHOD IN THIS FOOD CONTROLLER (Jerson) ***/
    public static void main(String[] args) {
//        FoodController controller = new FoodController();

        // 1. ADD FOOD
//        Response<Food> addFoodResponse = controller.addFood("Test", 12.99, 1);
//        if (addFoodResponse.isSuccess()) {
//            System.out.println("Food added successfully: " + addFoodResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error adding food: " + addFoodResponse.getMessage());
//        }

        // 2. GET ALL FOODS
//        Response<List<Food>> foodsResponse = controller.getAllFoods();
//        if (foodsResponse.isSuccess()) {
//            List<Food> foods = foodsResponse.getData();
//            System.out.printf("\n\n%-10s %-30s %-10s %-20s%n", "Food ID", "Food Name", "Price", "Category");
//            for (Food food : foods) {
//                //System.out.println(food.getFoodId() + ".\t" + food.getFoodName());
//                food.displayInfo();
//            }
//            System.out.println("Total Records: " + foods.size());
//        } else {
//            System.out.println("Error: " + foodsResponse.getMessage());
//        }
        
        //3.  GET ALL FOODS BY CATEGORY
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

        // 5. GET FOOD BY ID
//        Response<Food> getFoodByIdResponse = controller.getFoodById(9);
//        if (getFoodByIdResponse.isSuccess()) {
//            Food food = getFoodByIdResponse.getData();
//            System.out.println("Food retrieved: " + food.getFoodName());
//        } else {
//            System.out.println("Error retrieving food: " + getFoodByIdResponse.getMessage());
//        }

        // 6. UPDATE FOOD
//        Response<Food> updateFoodResponse = controller.updateFood(9, "Updated Test", 10.99, 2);
//        if (updateFoodResponse.isSuccess()) {
//            System.out.println("Food updated: " + updateFoodResponse.getData().getFoodName());
//        } else {
//            System.out.println("Error updating food: " + updateFoodResponse.getMessage());
//        }

        // 7. DELETE FOOD
//        Response<String> deleteFoodResponse = controller.deleteFood(9);
//        if (deleteFoodResponse.isSuccess()) {
//            System.out.println("Food deleted successfully");
//        } else {
//            System.out.println("Error deleting food: " + deleteFoodResponse.getMessage());
//        }
    }
}
