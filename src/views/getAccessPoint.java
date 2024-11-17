/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import helpers.Response;
import java.util.List;
import javax.swing.JOptionPane;
import models.Ingredient;
import static views.RunnerTest.INGREDIENT_CONTROLLER;
import static views.RunnerTest.SCANNER;

/**
 *
 * @author Admin
 */
public class getAccessPoint {
    public static void main(String[] args) {
        getAccessPoint access = new getAccessPoint();
        access.showAllStocks();
    }
      void showLowStock() {
        // Fetch ingredients with low stock
        Response<List<Ingredient>> ingredientsWithLowStocksResponse = INGREDIENT_CONTROLLER.getAllIngredientsLowStocks();
        if (ingredientsWithLowStocksResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsWithLowStocksResponse.getData();
            //displayIngredientItems(ingredients);
            JOptionPane.showMessageDialog(null, displayIngredientItems(ingredients));
        } else {
            JOptionPane.showMessageDialog(null, ingredientsWithLowStocksResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        }
    }

      void showOneItemInformation() {
        //System.out.print("Enter ingredient id: ");
        String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");

        Response<Ingredient> ingredientsResponse = INGREDIENT_CONTROLLER.getIngredientById(Integer.parseInt(ingredientIDInput));
        if (ingredientsResponse.isSuccess()) {
            Ingredient ingredient = ingredientsResponse.getData();
            //ingredient.display();
            JOptionPane.showMessageDialog(null, ingredient.display());
        } else {
            //System.out.println("Error retrieving ingredient: " + ingredientsResponse.getMessage());
            JOptionPane.showMessageDialog(null,"Error retrieving ingredient: " + ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
        }
    }

      void showAllStocks() {
        // Fetch all ingredients (not just low stock)
        Response<List<Ingredient>> ingredientsResponse = INGREDIENT_CONTROLLER.getAllIngredients();
        if (ingredientsResponse.isSuccess()) {
            List<Ingredient> ingredients = ingredientsResponse.getData();
            JOptionPane.showMessageDialog(null, displayIngredientItems(ingredients));
        } else {
            JOptionPane.showMessageDialog(null, ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
      void checkForReorder() {
        //System.out.print("Enter ingredient id: ");
        String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");
        
        
        Response<String> reorderCheckResponse = INGREDIENT_CONTROLLER.checkReorderNeed(Integer.parseInt(ingredientIDInput));
        if(reorderCheckResponse.isSuccess()) {
            //System.out.println(reorderCheckResponse.getMessage());
            JOptionPane.showMessageDialog(null, reorderCheckResponse.getMessage());
        } else {
            //System.out.println("Error checking ingredient: " + reorderCheckResponse.getMessage());
            JOptionPane.showMessageDialog(null,"Error checking ingredient: " + reorderCheckResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
        }
    }

      void updateStocks() {
        //System.out.print("Enter ingredient id: ");
        String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");

        //System.out.print("Enter quantity: ");
        String ingredientQuantityInput = JOptionPane.showInputDialog(null,"Enter quantity: ");
        
        Response<Ingredient> updateStocksResponse = INGREDIENT_CONTROLLER.updateQuantity(
            Integer.parseInt(ingredientIDInput),
            Integer.parseInt(ingredientQuantityInput)
        );
        if(updateStocksResponse.isSuccess()) {
            //System.out.println(updateStocksResponse.getMessage());
            JOptionPane.showMessageDialog(null, updateStocksResponse.getMessage());
        } else {
            //System.out.println("Error updating ingredient: " + updateStocksResponse.getMessage());
            JOptionPane.showMessageDialog(null, "Error updating ingredient: " + updateStocksResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
        }
    }
    
      void updateReorderPoints() {
        //System.out.print("Enter ingredient id: ");
        String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");

       // System.out.print("Enter reorder points: ");
        String ingredientReorderPointsInput = JOptionPane.showInputDialog(null,"Enter reorder points: ");
        
        Response<Ingredient> updateReorderPointsResponse = INGREDIENT_CONTROLLER.updateReorderPoints(
            Integer.parseInt(ingredientIDInput),
            Integer.parseInt(ingredientReorderPointsInput)
        );
        if(updateReorderPointsResponse.isSuccess()) {
            //System.out.println(updateReorderPointsResponse.getMessage());
            JOptionPane.showMessageDialog(null, updateReorderPointsResponse.getMessage());
        } else {
            //System.out.println("Error updating ingredient: " + updateReorderPointsResponse.getMessage());
            JOptionPane.showMessageDialog(null, "Error updating ingredient: " + updateReorderPointsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String displayIngredientItems(List<Ingredient> ingredients) {
        StringBuilder output = new StringBuilder();
    
        // Header
        output.append("Ingredient ID\t");
        output.append("Name\t");
        output.append("Current Stock\t");
        output.append("Reorder Point\n");
    
        // Rows
        for (Ingredient ingredient : ingredients) {
            output.append(String.format("%-10s %-30s $%-10s %-20s%n", 
                ingredient.getIngredientId(), 
                ingredient.getIngredientName(),
                ingredient.getQuantity(), 
                ingredient.getReorderPoint()));
        }
    
        // Footer
        output.append("Total Records: ").append(ingredients.size());
    
        return output.toString();
    }
}
