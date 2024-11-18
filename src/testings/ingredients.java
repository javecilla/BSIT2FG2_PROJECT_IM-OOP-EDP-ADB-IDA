package testings;

import models.Ingredient;
import controllers.IngredientController;
import helpers.Response;
import java.util.List;

public class ingredients {
    public static void main(String[] args) {
        IngredientController controller = new IngredientController();
        
        //1. GET ALL INGREDIENT RECORDS                
        Response<List<Ingredient>> ingredientsResponse = controller.getAllIngredients();
        if (ingredientsResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsResponse.getData();
            for (Ingredient ingredient : ingredients) {
                //ingredient.display();
                System.out.println("\n[Ingredient Information]:");
                System.out.println("\tId: " + ingredient.getIngredientId());
                System.out.println("\tName: " + ingredient.getIngredientName());
                System.out.println("\tCurrent Stock: " + ingredient.getQuantity());
                System.out.println("\tReorder Points: " + ingredient.getReorderPoint());
                System.out.println("[Supplier Information]:");
                System.out.println("\tName: " + ingredient.getSupplierName());
                System.out.println("\tContact: " + ingredient.getContactNumber());
                System.out.println("\tAddress: " + ingredient.getAddress() + "\n");
            }
            System.out.println("Total Records: " + ingredients.size());
        } else {
            System.out.println("Error: " + ingredientsResponse.getMessage());
        }

        // 2. UPDATE FOOD QUANTITY/STOCKS
        int testingId = 1;
        int incrementQuantity = 10; //if magda dagdag ng quantity or stocks, dapat yung value na p-provide sa parameter is positve
        int decrementQuantity = -10;  //unless if mag babawas naman ang value na p-provide ni user is negative
        
//        Response<Ingredient> updateStocksResponse = controller.updateQuantity(testingId, decrementQuantity);
//        if(updateStocksResponse.isSuccess()) {
//            System.out.println(updateStocksResponse.getMessage());
//        } else {
//            System.out.println("Error updating ingredient: " + updateStocksResponse.getMessage());
//        }
        
        // 3. UPDATE FOOD REODER POINTS
        //same with reoder points
//        Response<Ingredient> updateReorderPointsResponse = controller.updateReorderPoints(testingId, incrementQuantity);
//        if(updateReorderPointsResponse.isSuccess()) {
//            System.out.println(updateReorderPointsResponse.getMessage());
//        } else {
//            System.out.println("Error updating ingredient: " + updateReorderPointsResponse.getMessage());
//        }
        
        // 4. CHECKING REORDER
        //pag mag check if need na ba mag reoder sa isang item or not
//        Response<String> reorderCheckResponse = controller.checkReorderNeed(testingId);
//        if(reorderCheckResponse.isSuccess()) {
//            System.out.println(reorderCheckResponse.getMessage());
//        } else {
//            System.out.println("Error checking ingredient: " + reorderCheckResponse.getMessage());
//        }
        
        //5. GET ALL INGREDIENT RECORDS WITH LOW STOCKS             
        Response<List<Ingredient>> ingredientsWithLowStocksResponse = controller.getAllIngredientsLowStocks();
        if (ingredientsWithLowStocksResponse.isSuccess()) {
             List<Ingredient> ingredients = ingredientsWithLowStocksResponse.getData();
//            for (Ingredient ingredient : ingredients) {
//                //ingredient.display();
//                System.out.println("\n[Ingredient Information]:");
//                System.out.println("\tId: " + ingredient.getIngredientId());
//                System.out.println("\tName: " + ingredient.getIngredientName());
//                System.out.println("\tCurrent Stock: " + ingredient.getQuantity());
//                System.out.println("\tReorder Points: " + ingredient.getReorderPoint());
//                System.out.println("[Supplier Information]:");
//                System.out.println("\tName: " + ingredient.getSupplierName());
//                System.out.println("\tContact: " + ingredient.getContactNumber());
//                System.out.println("\tAddress: " + ingredient.getAddress() + "\n");
//            }
//            System.out.println("Total Records: " + ingredients.size());

            System.out.printf("\n%-10s %-30s %-10s %-20s%n", "Ingredient ID", "Name", "Current Stock", "Reorder Point");
            for (Ingredient ingredient : ingredients) {
                System.out.printf("%-10s %-30s $%-10s %-20s%n", 
                    ingredient.getIngredientId(), 
                    ingredient.getIngredientName(), 
                    ingredient.getQuantity(), 
                    ingredient.getReorderPoint()
                );
            }
            System.out.println("Total Records: " + ingredients.size());
        } else {
            System.out.println("Error: " + ingredientsWithLowStocksResponse.getMessage());
        }

    }
}
