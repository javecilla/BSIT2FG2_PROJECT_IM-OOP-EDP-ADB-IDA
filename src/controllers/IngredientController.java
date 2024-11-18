package controllers;

import interfaces.IOperatorsValidators;
import models.Ingredient;
import models.Supplier;
import models.Admin;
import models.User;
import services.IngredientService;
import services.SupplierService;
import services.UserService;
import helpers.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

public class IngredientController implements IOperatorsValidators<Ingredient> {
    protected final IngredientService ingredientService;
    protected final SupplierService supplierService;
    protected final UserService userService;
    
    public IngredientController() {
        this.ingredientService = new IngredientService();
        this.supplierService = new SupplierService();
        this.userService = new UserService();
    }

    // Add a new ingredient
    public Response<Ingredient> addIngredient(String ingredientName, int quantity, int reorderPoint, int supplierId, int adminId) {
        Admin admin = new Admin(
            adminId,
            null,
            new User(0, "", "", "")
        );

        Ingredient newIngredient = new Ingredient(
            0, 
            ingredientName.trim(),
            quantity,
            reorderPoint,
            new Supplier(supplierId, null, null, null) 
        );

        newIngredient.setAdmin(admin);

        // Validate before creating
        Response<Ingredient> validationResponse = validateCreate(newIngredient);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }

        try {
            //validate if ingredient already exists
            if(ingredientService.isIngredientExists(ingredientName)) {
                return Response.error("Error: Ingredient '" + ingredientName + "' already exists!");
            }            
            if(!supplierService.isSupplierExists(supplierId)) {
                return Response.error("Error: Supplier with ID " + supplierId + " does not exist!");
            }
            if(!userService.isAdminExists(adminId)) {
                return Response.error("Error: Admin with ID " + adminId + " does not exist!");
            }
            
            if (ingredientService.create(newIngredient)) {
                return Response.success("Ingredient created successfully!", newIngredient);
            } else {
                return Response.error("Failed to create ingredient. Check validation messages for more details.");
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    // Get all ingredients
    public Response<List<Ingredient>> getAllIngredients() {
        try {
            List<Ingredient> ingredients = ingredientService.getAll();

            if (ingredients == null || ingredients.isEmpty()) {
                return Response.success("No ingredients found", Collections.emptyList());
            }

            return Response.success("Ingredients retrieved successfully", ingredients);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    
    // Get all ingredients with low stock or quantity
    public Response<List<Ingredient>> getAllIngredientsLowStocks() {
        try {
            List<Ingredient> ingredients = ingredientService.getAllLowStock();

            if (ingredients == null || ingredients.isEmpty()) {
                System.out.println("No ingredients found or response is empty.");
                return Response.success("No ingredients found", Collections.emptyList());
            }

            return Response.success("Ingredients retrieved successfully", ingredients);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }


    // Get ingredient by ID
    public Response<Ingredient> getIngredientById(int id) {
        try {
            Ingredient ingredient = ingredientService.getById(id);

            if (ingredient == null) {
                return Response.error("Ingredient not found with ID: " + id);
            }

            return Response.success("Ingredient retrieved successfully", ingredient);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }
    
    //check if reorder is needed for an ingredient based on current stock and reorder point
    public Response<String> checkReorderNeed(int id) {
        try {
            Ingredient ingredient = ingredientService.getById(id);
            if(ingredient == null) {
                return Response.error("Ingredient not found with ID: " + id);
            }

            //get the current stock (quantity) and reorder point for the ingredient
            int currentQuantity = ingredient.getQuantity();
            int reorderPoint = ingredient.getReorderPoint();

            //check if the current stock is less than or nag equal sa reorder point
            if(currentQuantity <= reorderPoint) {
                //if current stock is less than reorder point, notify that reorder is needed
                return Response.success("Reorder needed: Current stock is below reorder point. Current stock: " 
                                        + currentQuantity + ", Reorder point: " + reorderPoint);
            } else {
                //if the stock is sufficient, notify that no reorder is needed
                return Response.success("No reorder needed: Current stock is sufficient. Current stock: " 
                                        + currentQuantity + ", Reorder point: " + reorderPoint);
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }


    // Update ingredient
    public Response<Ingredient> updateIngredient(int ingredientId, String ingredientName, int quantity, int reorderPoint, int supplierId, int adminId) {
        Admin admin = new Admin(
            adminId,
            null,
            new User(0, "", "", "")
        );

        Ingredient updatedIngredient = new Ingredient(
            0, 
            ingredientName.trim(),
            quantity,
            reorderPoint,
            new Supplier(supplierId, null, null, null) 
        );

        updatedIngredient.setAdmin(admin);

        // Validate before updating
        Response<Ingredient> validationResponse = validateUpdate(updatedIngredient);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }

        try {
            // Check if the ingredient exists
            Ingredient existingIngredient = ingredientService.getById(ingredientId);
            if (existingIngredient == null) {
                return Response.error("Ingredient not found with ID: " + ingredientId);
            }

            boolean isUpdated = ingredientService.update(updatedIngredient);

            if (isUpdated) {
                return Response.success("Ingredient updated successfully", updatedIngredient);
            } else {
                return Response.error("Failed to update ingredient.");
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    // Update the quantity or current stocks
    public Response<Ingredient> updateQuantity(int id, int quantity) {
        if (quantity == 0) return Response.error("Quantity change cannot be zero.");

        try {
            // Check the current ingredient to get the current quantity
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) return Response.error("Ingredient not found with ID: " + id);

            // Calculate the new quantity
            int currentQuantity = ingredient.getQuantity();
            int newQuantity = currentQuantity + quantity;

            if (newQuantity < 0) return Response.error("New quantity cannot be negative.");

            boolean isUpdated = ingredientService.updateQuantity(id, newQuantity);

            if (isUpdated) {
                return Response.success("Ingredient quantity updated successfully. New quantity: " + newQuantity);
            } else {
                return Response.error("Failed to update ingredient quantity.");
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    // Update the reorder points
    public Response<Ingredient> updateReorderPoints(int id, int reorderPoints) {
        if (reorderPoints == 0) return Response.error("Reorder points change cannot be zero.");

        try {
            // Check the current ingredient to get the current reorder points
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) return Response.error("Ingredient not found with ID: " + id);

            // Calculate the new reorder points
            int currentReorderPoints = ingredient.getReorderPoint();
            int newReorderPoints = currentReorderPoints + reorderPoints;

            if (newReorderPoints < 0) return Response.error("New reorder points cannot be negative.");

            boolean isUpdated = ingredientService.updateReorderPoint(id, newReorderPoints);

            if (isUpdated) {
                return Response.success("Ingredient reorder points updated successfully. New reorder points: " + newReorderPoints);
            } else {
                return Response.error("Failed to update ingredient reorder points.");
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    // Delete ingredient
    public Response<Boolean> deleteIngredient(int id) {
        Response<Boolean> validationResponse = validateDelete(id);
        if (!validationResponse.isSuccess()) {
            return Response.error("Validation failed: " + validationResponse.getMessage());
        }

        try {
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) {
                return Response.error("Ingredient not found with ID: " + id);
            }

            boolean isDeleted = ingredientService.delete(id);

            if (isDeleted) {
                return Response.success("Ingredient deleted successfully", true);
            } else {
                return Response.error("Failed to delete ingredient.");
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    // Implementing the validation methods from IOperatorsValidators

    @Override
    public Response<Ingredient> validateCreate(Ingredient ingredient) {
        if (ingredient.getIngredientName().isEmpty()) {
            return Response.error("Ingredient name cannot be empty.");
        }
        if (ingredient.getQuantity() < 0) {
            return Response.error("Quantity cannot be negative.");
        }
        if (ingredient.getReorderPoint() < 0) {
            return Response.error("Reorder point cannot be negative.");
        }
        return Response.success("Validation successful", ingredient);
    }

    @Override
    public Response<Ingredient> validateUpdate(Ingredient ingredient) {
        if (ingredient.getIngredientName().isEmpty()) {
            return Response.error("Ingredient name cannot be empty.");
        }
        if (ingredient.getQuantity() < 0) {
            return Response.error("Quantity cannot be negative.");
        }
        if (ingredient.getReorderPoint() < 0) {
            return Response.error("Reorder point cannot be negative.");
        }
        return Response.success("Validation successful", ingredient);
    }

    @Override
    public Response<Boolean> validateDelete(int id) {
        if (id <= 0) {
            return Response.error("Invalid ID!");
        }
        
        return Response.success("Validation successful", true);
    }
}
