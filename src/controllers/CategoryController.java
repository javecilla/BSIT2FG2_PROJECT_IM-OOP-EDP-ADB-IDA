
package controllers;

import models.Category;
import services.CategoryService;
import helpers.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;
import interfaces.IOperatorsValidators;

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
            if(categoryService.create(category)) {
                return Response.success("Category created successfully!", category);
            } else {
                return Response.error("Failed to category food.");
            }
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
        
            if(categoryService.update(updatedCategory)) {
                return Response.success("Category updated successfully", updatedCategory);
            } else {
                return Response.error("Failed to update category");
            }
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

            if(categoryService.delete(id)) {
                return Response.success("Category deleted successfully", null);
            } else {
                return Response.error("Failed to delete category");
            }
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
    
     /*** SAMPLE USAGE IN EACH METHOD IN THIS CATEGORY CONTROLLER (Jerson) ***/
    public static void main(String[] args) {
//        CategoryController controller = new CategoryController();

        // 1. ADD RECORD
//        Response<Category> addCategoryResponse = controller.addCategory("Test1");
//        if (addCategoryResponse.isSuccess()) {
//            System.out.println("Category added successfully: " + addCategoryResponse.getData().getCategoryName());
//        } else {
//            System.out.println("Error adding category: " + addCategoryResponse.getMessage());
//        }

        // 2. GET ALL RECORDS
//        Response<List<Category>> categoriesResponse = controller.getAllCategories();
//        if (categoriesResponse.isSuccess()) {
//            List<Category> categories = categoriesResponse.getData();
//            System.out.printf("\n\n%-10s %-30s%n", "Category ID", "Category Name");
//            for (Category category : categories) {
//                category.displayInfo();
//            }
//            System.out.println("Total Records: " + categories.size());
//        } else {
//            System.out.println("Error: " + categoriesResponse.getMessage());
//        }

        // 3. GET ALL RECORDS BY FILTER
        
        // 4. GET RECORD BY ID
//        Response<Category> getCategoryByIdResponse = controller.getCategoryById(1);
//        if (getCategoryByIdResponse.isSuccess()) {
//            Category category = getCategoryByIdResponse.getData();
//            System.out.println("Category retrieved: " + category.getCategoryName());
//        } else {
//            System.out.println("Error retrieving category: " + getCategoryByIdResponse.getMessage());
//        }

        // 5. UPDATE RECORD
//        Response<Category> updateCategoryResponse = controller.updateCategory(4, "Updated Test1");;
//        if (updateCategoryResponse.isSuccess()) {
//            System.out.println("Food updated: " + updateCategoryResponse.getData().getCategoryName());
//        } else {
//            System.out.println("Error updating food: " + updateCategoryResponse.getMessage());
//        }

        // 6. DELETE RECORD
//        Response<String> deleteCategoryResponse = controller.deleteCategory(4);
//        if (deleteCategoryResponse.isSuccess()) {
//            System.out.println("Category deleted successfully");
//        } else {
//            System.out.println("Error deleting category: " + deleteCategoryResponse.getMessage());
//        }
    }
}
