/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import models.User;
import models.Ingredient;
import models.Category;
import controllers.UserController;
import controllers.IngredientController;
import controllers.CategoryController;
import helpers.Response;
import enums.UserRoles;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class getAccessPoint {
    protected static final UserController LOGIN_CONTROLLER = new UserController();
    protected static final IngredientController INGREDIENT_CONTROLLER = new IngredientController();
    protected static final CategoryController CATEGORY_CONTROLLER = new CategoryController();
    
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
        while(true){
          try{
              //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");
        
            if(ingredientIDInput == null){
                return;
            }

            Response<Ingredient> ingredientsResponse = INGREDIENT_CONTROLLER.getIngredientById(Integer.parseInt(ingredientIDInput));
            if (ingredientsResponse.isSuccess()) {
                Ingredient ingredient = ingredientsResponse.getData();
                //ingredient.display();
                JOptionPane.showMessageDialog(null, ingredient.display());
            } else {
                //System.out.println("Error retrieving ingredient: " + ingredientsResponse.getMessage());
                JOptionPane.showMessageDialog(null,"Error retrieving ingredient: " + ingredientsResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
            }
            
          }catch(NumberFormatException er){
            JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
          }
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
        //System.out.print("Enter ingredient id: ");while(true){
          while(true){
            try{
                String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");
        
                if(ingredientIDInput == null){
                    return;
                }
        
                    Response<String> reorderCheckResponse = INGREDIENT_CONTROLLER.checkReorderNeed(Integer.parseInt(ingredientIDInput));
                    if(reorderCheckResponse.isSuccess()) {
                    //System.out.println(reorderCheckResponse.getMessage());
                    JOptionPane.showMessageDialog(null, reorderCheckResponse.getMessage());
                } else {
                    //System.out.println("Error checking ingredient: " + reorderCheckResponse.getMessage());
                    JOptionPane.showMessageDialog(null,"Error checking ingredient: " + reorderCheckResponse.getMessage(), "MOMMY'S VARIETY STORE", JOptionPane.ERROR_MESSAGE);
                }
                    
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
    }

      void updateStocks(){
        while(true){
          try{
            //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");
        
            if(ingredientIDInput == null){
                return;
            }

            //System.out.print("Enter quantity: ");
            String ingredientQuantityInput = JOptionPane.showInputDialog(null,"Enter quantity: ");
        
            if(ingredientQuantityInput == null){
                return;
            }
        
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
            }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
          }
        
    }
    
      void updateReorderPoints() {
        while(true){
          try{
            //System.out.print("Enter ingredient id: ");
            String ingredientIDInput = JOptionPane.showInputDialog(null,"Enter ingredient id: ");
        
            if(ingredientIDInput == null){
                return;
            }

            // System.out.print("Enter reorder points: ");
            String ingredientReorderPointsInput = JOptionPane.showInputDialog(null,"Enter reorder points: ");
        
            if(ingredientReorderPointsInput == null){
                return;
            }
        
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
          }catch(NumberFormatException er){
                JOptionPane.showMessageDialog(null, er, "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
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
    
    void showProfile(User user) {
        /*        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Address: " + user.getFullAddress());*/
        JOptionPane.showMessageDialog(null, "YOUR PROFILE:\n\nFull Name: " + user.getFullName() + "\nAddress: " + user.getFullAddress(),"MOMMY'S VARIETY STORE", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
