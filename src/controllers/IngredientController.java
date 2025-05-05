package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Collections;

import models.Ingredient;
import models.Supplier;
//import models.Admin;
import models.User;
import services.IngredientService;
import services.SupplierService;
import services.UserService;
import helpers.Response;
import interfaces.IOperatorsValidators;

public class IngredientController implements IOperatorsValidators<Ingredient> {
    private final IngredientService ingredientService;
    private final SupplierService supplierService;
    private final UserService userService;
   
    public IngredientController() {
        this.ingredientService = new IngredientService();
        this.supplierService = new SupplierService();
        this.userService = new UserService();    
    }

    public Response<Ingredient> addIngredient(String ingredientName, int quantity, int reorderPoint, int supplierId, int adminId) {
//        Admin admin = new Admin(
//            adminId,
//            null,
//            new User(0, "", "", "", "", "")
//        );
//
//        Ingredient newIngredient = new Ingredient(
//            0, 
//            ingredientName.trim(),
//            quantity,
//            reorderPoint
//        );
//        newIngredient.setSupplier(new Supplier(supplierId, null, null, null));
//        newIngredient.setAdmin(admin);

        Ingredient newIngredient = new Ingredient();
        newIngredient.setIngredientName(ingredientName);
        newIngredient.setQuantity(quantity);
        newIngredient.setReorderPoint(reorderPoint);
        
        Supplier s = new Supplier();
        s.setSupplierId(supplierId);
        newIngredient.setSupplier(s);
        
        User a = new User();
        a.setUserId(adminId);
        newIngredient.setUserAdmin(a);

        Response<Ingredient> validationResponse = validateCreate(newIngredient);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }

        try {
            if(ingredientService.isIngredientExistsByName(ingredientName)) {
               return Response.error("Error: Ingredient '" + ingredientName + "' already exists!");
            }
            
//            if(!supplierService.isSupplierExistsById(supplierId)) {
//                return Response.error("Error: Supplier with ID " + supplierId + " does not exist!");
//            }
            Supplier supplier  = supplierService.getById(supplierId);
            if (supplier == null) return Response.error("Supplier not found with ID: " + supplierId);
            
//            if(!userService.isUserExistsById(adminId)) {
//              return Response.error("Error: Admin with ID " + adminId + " does not exist!");
//            }
            User admin  = userService.getById(adminId);
            if (admin == null) return Response.error("User admin not found with ID: " + adminId);

            boolean isCreated = ingredientService.create(newIngredient);
            
            return (isCreated) 
                ? Response.success("Ingredient created successfully!", newIngredient)
                : Response.error("Failed to create ingredient. Check validation messages for more details.");
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

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
    
    
    public Response<List<Ingredient>> getAllIngredientsLowStocks() {
        try {
            List<Ingredient> ingredients = ingredientService.getAllLowStock();

            if (ingredients == null || ingredients.isEmpty()) {
                return Response.success("No ingredients found", Collections.emptyList());
            }

            return Response.success("Ingredients retrieved successfully", ingredients);
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

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

            if(currentQuantity <= reorderPoint) {
                //if current stock is less than reorder point, notify that reorder is needed
                return Response.success("Reorder needed: Current stock is below reorder point. Current stock: " + currentQuantity + ", Reorder point: " + reorderPoint);
            } else {
                //else the stock is sufficient, notify that no reorder is needed
                return Response.success("No reorder needed: Current stock is sufficient. Current stock: " + currentQuantity + ", Reorder point: " + reorderPoint);
            }
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }


    // Update ingredient
    public Response<Ingredient> updateIngredient(int ingredientId, String ingredientName, int quantity, int reorderPoint, int supplierId, int adminId) {
//        Admin admin = new Admin(
//            adminId,
//            null,
//            new User(0, "", "", "", "", "")
//        );
//
//        Ingredient updatedIngredient = new Ingredient(
//            0, 
//            ingredientName.trim(),
//            quantity,
//            reorderPoint
//        );
//        updatedIngredient.setSupplier(new Supplier(supplierId, null, null, null));
//        updatedIngredient.setAdmin(admin);

        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setIngredientId(ingredientId);
        updatedIngredient.setIngredientName(ingredientName);
        updatedIngredient.setQuantity(quantity);
        updatedIngredient.setReorderPoint(reorderPoint);
        
        Supplier s = new Supplier();
        s.setSupplierId(supplierId);
        updatedIngredient.setSupplier(s);
        
        User a = new User();
        a.setUserId(adminId);
        updatedIngredient.setUserAdmin(a);

        Response<Ingredient> validationResponse = validateUpdate(updatedIngredient);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }

        try {
//            if(!ingredientService.isIngredientExistsById(ingredientId)) {
//               return Response.error("Error: Ingredient with ID " + ingredientId + " does not exist!");
//            }
            Ingredient ingredient = ingredientService.getById(ingredientId);
            if (ingredient == null) return Response.error("Ingredient not found with ID: " + ingredientId);
            
//            if(!supplierService.isSupplierExistsById(supplierId)) {
//                return Response.error("Error: Supplier with ID " + supplierId + " does not exist!");
//            }
            Supplier supplier  = supplierService.getById(supplierId);
            if (supplier == null) return Response.error("Supplier not found with ID: " + supplierId);
            
//            if(!userService.isUserExistsById(adminId)) {
//              return Response.error("Error: Admin with ID " + adminId + " does not exist!");
//            }
            User admin  = userService.getById(adminId);
            if (admin == null) return Response.error("User admin not found with ID: " + adminId);
            
            boolean isUpdated = ingredientService.update(updatedIngredient);
            return (isUpdated)
                ? Response.success("Ingredient updated successfully", updatedIngredient)
                : Response.error("Failed to update ingredient.");
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    // Update the quantity or current stocks
    public Response<Ingredient> updateQuantity(int id, int quantity) {
        if (quantity == 0) return Response.error("Quantity change cannot be zero.");

        try {
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) return Response.error("Ingredient not found with ID: " + id);

            int currentQuantity = ingredient.getQuantity();
            int newQuantity = currentQuantity + quantity;

            if (newQuantity < 0) return Response.error("New quantity cannot be negative. Current stock: " + currentQuantity + ", Attempted change: " + quantity);

            boolean isUpdated = ingredientService.updateQuantity(id, newQuantity);

            return (isUpdated)
                ? Response.success("Ingredient quantity updated successfully. New quantity: " + newQuantity)
                : Response.error("Failed to update ingredient quantity.");
        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

 
    // Update the reorder points
    public Response<Ingredient> updateReorderPoints(int id, int reorderPoints) {
        if (reorderPoints == 0) return Response.error("Reorder points change cannot be zero.");

        try {
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) return Response.error("Ingredient not found with ID: " + id);

            int currentReorderPoints = ingredient.getReorderPoint();
            int newReorderPoints = currentReorderPoints + reorderPoints;

            if (newReorderPoints < 0) return Response.error("New reorder points cannot be negative.");

            boolean isUpdated = ingredientService.updateReorderPoint(id, newReorderPoints);
            
            return (isUpdated)
                ? Response.success("Ingredient reorder points updated successfully. New reorder points: " + newReorderPoints)
                : Response.error("Failed to update ingredient reorder points.");

        } catch (SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

    public Response<Boolean> deleteIngredient(int id) {
        Response<Boolean> validationResponse = validateDelete(id);
        if (!validationResponse.isSuccess()) {
            return Response.error("Validation failed: " + validationResponse.getMessage());
        }

        try {
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) return Response.error("Ingredient not found with ID: " + id);

            boolean isDeleted = ingredientService.delete(id);

            return (isDeleted)
                ? Response.success("Ingredient deleted successfully")
                : Response.error("Failed to delete ingredient.");
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