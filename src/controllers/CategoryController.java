package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

import models.Category;
import services.CategoryService;
import interfaces.IOperatorsValidators;
import helpers.Response;

public class CategoryController implements IOperatorsValidators<Category> {
    protected final CategoryService categoryService;
    
    public CategoryController() {
        this.categoryService = new CategoryService();
    }
    
    // Create a new category item
    public Response<Category> addCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName.trim());
       
        Response<Category> validationResponse = validateCreate(category);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        try { 
            if(categoryService.isCategoryExists(categoryName)) {
              return Response.error("Error: Category '" + categoryName + "' already exists!");
            }
            
            boolean isCreated = categoryService.create(category);
            
            return (isCreated)
                ? Response.success("Category created successfully!", category)
                : Response.error("Failed to category food.");
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Get all categories
    public Response<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAll();
            
            if(categories == null || categories.isEmpty()) {
                return Response.success("No categories found", Collections.emptyList());
            }
            
            return Response.success("Categories retrieved successfully", categories);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Get category by ID
    public Response<Category> getCategoryById(int id) {
        try {
            Category category = categoryService.getById(id);
            
            if(category == null) {
                return Response.error("Category not found with ID: " + id);
            }
            
            return Response.success("Category retrieved successfully", category);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Update category
    public Response<Category> updateCategory(int categoryId, String categoryName) {
        Category updatedCategory = new Category(categoryId, categoryName);
        
        // Validate Food data for update
        Response<Category> validationResponse = validateUpdate(updatedCategory);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }
        
        try {
            // Check if the category exist 
            Category existingCategory = categoryService.getById(categoryId);
            if(existingCategory == null) {
                return Response.error("Category not found with ID: " + categoryId);
            }
            
            boolean isUpdated = categoryService.update(updatedCategory);
            
            return (isUpdated)
                ? Response.success("Category updated successfully", updatedCategory)
                : Response.error("Failed to update category");

        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    // Delete category
    public Response<String> deleteCategory(int id) {
        try {
            // Validate delete operation
            Response<Boolean> validationResponse = validateDelete(id);
            if (!validationResponse.isSuccess()) {
                return Response.error("Validation failed: " + validationResponse.getMessage());
            }
            
            // Check if the food exists
            Category existingFood = categoryService.getById(id);
            if (existingFood == null) {
                return Response.error("Category not found with ID: " + id);
            }
            
            boolean isDeleted = categoryService.delete(id);
            
            return (isDeleted)
                ? Response.success("Category deleted successfully", null)
                : Response.error("Failed to delete category");

        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    @Override
    public Response<Category> validateCreate(Category category) {
        if(category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return Response.error("Category name cannot be empty!");
        }
        
        return Response.success("Validation passed", category);
    }
    
    @Override
    public Response<Category> validateUpdate(Category category) {
        if(category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return Response.error("Category name cannot be empty");
        }

        if(category.getCategoryId() <= 0) {
            return Response.error("Invalid ID!");
        }
        
        return Response.success("Validation passed", category);
    }
    
    @Override
    public Response<Boolean> validateDelete(int id) {
        if (id <= 0) {
            return Response.error("Invalid ID!");
        }
        return Response.success("Validation passed", true);
    }
}
