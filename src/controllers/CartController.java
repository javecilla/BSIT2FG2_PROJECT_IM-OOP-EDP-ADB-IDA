package controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import models.Cart;
import models.CartItem;
import models.Food;
import models.SalesDetails;
import models.Customer;
import models.Ingredient;
import models.Recipe;
import models.Sale;
import models.Session;
import services.RecipeService;
import services.IngredientService;
import services.SaleService;
import services.SalesDetailsService;
import helpers.Response;
import helpers.Date;


public class CartController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final SaleService saleService;
    private final SalesDetailsService salesDetailsService;
    private final Cart cart;
    
    public CartController() {
        this.recipeService = new RecipeService();
        this.ingredientService = new IngredientService();
        this.saleService = new SaleService();
        this.salesDetailsService = new SalesDetailsService();
        this.cart = new Cart();
    }
    
    public Response<Cart> addToCart(int foodId, String foodName, double foodPrice, int quantity) {
        if(quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }
        
        try {
            //check first if the each ingredient quantity of the food selected 
            //is sufficient paba or hindi, if succient pa then allow to order
            //pag hindi return food is currentlt not available
            Recipe recipe = recipeService.getAllIngredientsInFood(foodId);
            if(recipe == null) return Response.error("Failed to fetch recipe details for food ID: " + foodId);
            
            for(Ingredient ingredient : recipe.getIngredients()) {
                Response<String> reorderCheckResponse = 
                        new IngredientController().checkReorderNeed(ingredient.getIngredientId());
                if(!reorderCheckResponse.isSuccess()) {
                    //System.out.println(reorderCheckResponse.getMessage());
                    return Response.error("Opps sorry, The food " + foodName + " you selected is currently not available.");
                } 
            }
        } catch(SQLException e) {
            return Response.error("Something went wrong during checkout: " + e.getMessage());
        }
        
        // Check if the food item is already in the cart
        boolean itemExists = false;
        for(CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                // Increase the quantity if the item already exists
                item.increaseQuantity(quantity);
                itemExists = true;
                break;
            }
        }

        if(!itemExists) {
            // Add a new item if it doesn't exist
            cart.addItem(new CartItem(foodId, foodName, foodPrice, quantity));
        }

        return Response.success("Item added to cart.", cart);
    }
    


    public Response<Cart> removeFromCart(int foodId) {
        CartItem itemToRemove = null;

        // Find the item by ID
        for(CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                itemToRemove = item;
                break;
            }
        }

        if(itemToRemove != null) {
            cart.removeItem(itemToRemove);
            return Response.success("Item removed from cart.", cart);
        } else {
            return Response.error("Item not found in the cart.");
        }
    }

    public Response<Cart> updateQuantity(int foodId, int quantity) {
        if(quantity <= 0) {
            return Response.error("Quantity must be greater than 0.");
        }

        for(CartItem item : cart.getItems()) {
            if (item.getFoodId() == foodId) {
                item.setQuantity(quantity);
                return Response.success("Quantity updated.", cart);
            }
        }

        return Response.error("Item not found in the cart.");
    }

    public Response<Cart> viewCart() {
        return Response.success("Cart retrieved successfully.", cart);
    }
    
    public Response<Cart> checkOutOrder() {
        // Check if the cart is empty
        if(cart == null || cart.getItems().isEmpty()) {
            return Response.error("The cart is empty. Please add items before checking out.");
        }

        try {
            Customer customer = Session.getLoggedInCustomer();
            //Create a sale record (once for the entire checkout)
            Sale sale = new Sale(
                Date.getCurrentDate(), 
                cart.getTotalAmount(), 
                customer
            );

            boolean isSaleCreated = saleService.create(sale);
            if(!isSaleCreated) {
                return Response.error("Failed to create a new sale record.");
            }

            //iterate over cart items
            for(CartItem item : cart.getItems()) {
           
                //get all ingredients information  base on the food id
                Recipe recipe = recipeService.getAllIngredientsInFood(item.getFoodId());
                if(recipe == null) {
                    return Response.error("Failed to fetch recipe details for food ID: " + item.getFoodId());
                }
                
                //get the recipe quantity record and multiply it by the customer's order quantity
                int totalQuantity = recipe.getRecipeQuantity() * item.getQuantity();
                
                //System.out.println("totalQuantity to reduce: " + totalQuantity + " for food " + item.getFoodName());
                //loop all the ingredients na bumubuo sa food
                //kase kada order sa food mag babawas ng quantity
                //per ingridients
                for(Ingredient ingredient : recipe.getIngredients()) {
                    // Calculate the new ingredient stock change
                    int quantityChange = -totalQuantity; // Negative because we’re reducing stock
                    //System.out.println("HALA: " + quantityChange);
                    if (totalQuantity <= 0 ) return Response.error("Quantity change cannot be negative or zero.");
     
                    Response<Ingredient> updateStocksResponse = new IngredientController().updateQuantity(
                            ingredient.getIngredientId(), quantityChange);
                    if(!updateStocksResponse.isSuccess()) {
                        return Response.error("Error updating ingredient: " + updateStocksResponse.getMessage());
                    } 
                }
            
                //insert sales details for each item
                boolean isSalesDetailsCreated = salesDetailsService.create(
                    new SalesDetails(
                        new Food(
                            item.getFoodId(), 
                            item.getFoodName(), 
                            item.getFoodPrice()
                        ),
                        sale, 
                        item.getQuantity()
                    )
                );
                if(!isSalesDetailsCreated) {
                    return Response.error("Failed to create sales details for food ID: " + item.getFoodId());
                }
            }
            
            //clear the cart after successful ng checkout
            cart.getItems().clear();
            
            return Response.success("Your order is successfully checked out! Thank you for your order.", null);
        } catch (SQLException e) {
            return Response.error("Something went wrong during checkout: " + e.getMessage());
        }
    }
    
    public Response<List<SalesDetails>> getOrderReports() {
        try {
            Customer customer = Session.getLoggedInCustomer();
            List<SalesDetails> salesDetals = salesDetailsService.getSalesDetailsByCustomer(customer.getCustomerId());
            
            if(salesDetals == null || salesDetals.isEmpty()) {
                return Response.success("No foods found", Collections.emptyList());
            }
            
            return Response.success("Sales details retrieved successfully", salesDetals);
        } catch(SQLException e) {
            return Response.error("Something went wrong: " + e.getMessage());
        }
    }

}
