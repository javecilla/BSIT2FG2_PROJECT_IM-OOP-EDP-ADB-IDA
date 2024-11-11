package views;

//import models.Food;
//import controllers.FoodController;
import models.Category;
import controllers.CategoryController;
import helpers.Response;
import java.util.List;

public class RunnerTest {
    public static void main(String[] args) {
        //FoodController controller = new FoodController();
        CategoryController controller = new CategoryController();

        // 1. ADD RECORD
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
        // 3. GET ALL RECORDS BY CATEGORY
        // 4. GET RECORD BY ID
        Response<Category> getCategoryByIdResponse = controller.getCategoryById(1);
        if (getCategoryByIdResponse.isSuccess()) {
            Category category = getCategoryByIdResponse.getData();
            System.out.println("Category retrieved: " + category.getCategoryName());
        } else {
            System.out.println("Error retrieving category: " + getCategoryByIdResponse.getMessage());
        }
        // 5. UPDATE RECORD
        // 6. DELETE RECORD
    }
}
