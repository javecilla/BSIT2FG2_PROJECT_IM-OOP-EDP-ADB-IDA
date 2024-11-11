
package controllers;

import models.Category;
import services.CategoryService;
import helpers.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

public class CategoryController {
    protected final CategoryService categoryService;
    
    public CategoryController() {
        this.categoryService = new CategoryService();
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
            return Response.error("Failed to retrieve categories: " + e.getMessage());
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
            return Response.error("Failed to retrieve Category: " + e.getMessage());
        }
    }
}
