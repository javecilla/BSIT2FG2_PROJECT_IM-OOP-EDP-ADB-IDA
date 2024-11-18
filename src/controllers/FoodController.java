package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

import models.Food;
import models.Category;
import services.FoodService;
import helpers.Response;
import interfaces.IOperatorsValidators;

public class FoodController implements IOperatorsValidators<Food> {
    protected final FoodService foodService;
    
    public FoodController() {
        this.foodService = new FoodService();
    }
    
    // Create a new food item
    public Response<Food> addFood(String foodName, double price, int categoryId) {
        // Create a Food object and assign values
        Food newFood = new Food(0, foodName.trim(), price, new Category(categoryId, null));
        
        // Validate Food data
        Response<Food> validationResponse = validateCreate(newFood);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        // Proceed with creation if validation passes
        try { 
            if(foodService.isFoodExists(foodName)) {
             return Response.error("Error: Food '" + foodName + "' already exists!");
            }
            
            boolean isCreated = foodService.create(newFood);
            
            return (isCreated) 
                ? Response.success("Food created successfully!", newFood)
                : Response.error("Failed to create food.");

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
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    public Response<List<Food>> getFoodsByCategory(int categoryId) {
        try {
            List<Food> foods = foodService.getByCategory(categoryId);
            
            if(foods == null || foods.isEmpty()) {
                return Response.success("No foods found", Collections.emptyList());
            }
            
            return Response.success("Foods retrieved successfully", foods);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
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
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Update food
    public Response<Food> updateFood(int foodId, String foodName, double price, int categoryId) {
        // Create a new Food object with updated details
        Food updatedFood = new Food(foodId, foodName.trim(), price, new Category(categoryId, null));
        
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
            
            boolean isUpdated = foodService.update(updatedFood);
            
            return (isUpdated) 
                ? Response.success("Food updated successfully", updatedFood)
                : Response.error("Failed to update food");
        
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
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
            
            boolean isDeleted = foodService.delete(id);
            
            return (isDeleted) 
                ? Response.success("Food deleted successfully", null)
                : Response.error("Failed to delete food");

        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
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
        
        if(food.getCategoryId() <= 0) {
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
        
        if(food.getFoodId() <= 0) {
            return Response.error("Invalid ID!");
        }
        
        if(food.getCategoryId() <= 0) {
            return Response.error("Category ID must be valid!");
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
}