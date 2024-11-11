package views;

import models.Category;
import controllers.CategoryController;
import helpers.Response;
import java.util.List;

public class RunnerTest {
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
